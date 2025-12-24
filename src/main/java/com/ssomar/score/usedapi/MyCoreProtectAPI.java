package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.coreprotect.config.Config;
import net.coreprotect.config.ConfigHandler;
import net.coreprotect.consumer.Queue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class MyCoreProtectAPI {

    // Cache for natural block lookups: key = "world:x:y:z", value = isNatural
    private static final Map<String, CachedResult> naturalBlockCache = new ConcurrentHashMap<>();
    private static final long CACHE_EXPIRY_MS = 5 * 60 * 1000; // 5 minutes
    private static final int LOOKUP_TIME_SECONDS = 604800; // 7 days

    private static class CachedResult {
        final boolean isNatural;
        final long timestamp;

        CachedResult(boolean isNatural) {
            this.isNatural = isNatural;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRY_MS;
        }
    }

    private static String getCacheKey(Block block) {
        return block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
    }

    private static CoreProtectAPI getCoreProtectAPI() {
        if (!SCore.hasCoreProtect) return null;

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
        if (plugin == null || !plugin.isEnabled()) return null;
        if (!(plugin instanceof CoreProtect)) return null;

        CoreProtectAPI api = ((CoreProtect) plugin).getAPI();
        if (!api.isEnabled()) return null;
        if (api.APIVersion() < 9) return null;

        return api;
    }

    public static void logRemoval(String user, Location location, Material type, BlockData blockData){
        if (SCore.hasCoreProtect) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

            if (!plugin.isEnabled()) return;

            // Check that CoreProtect is loaded
            if (!(plugin instanceof CoreProtect)) {
                return;
            }

            CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
            if (!CoreProtect.isEnabled()) {
                return;
            }

            // Check that a compatible version of the API is loaded
            if (CoreProtect.APIVersion() < 9) {
                return;
            }

            CoreProtect.logRemoval(user, location, type, blockData);
        }
    }

    /**
     * Checks if a block is natural (not player-placed) using cached async lookups.
     * Returns cached result if available, otherwise triggers async lookup and returns
     * a conservative default (false = not natural) until cache is populated.
     */
    public static boolean isNaturalBlock(Block block) {
        if (!SCore.hasCoreProtect) return false;

        CoreProtectAPI api = getCoreProtectAPI();
        if (api == null) return false;

        String cacheKey = getCacheKey(block);
        CachedResult cached = naturalBlockCache.get(cacheKey);

        // Return cached result if valid
        if (cached != null && !cached.isExpired()) {
            return cached.isNatural;
        }

        // Remove expired entry
        if (cached != null) {
            naturalBlockCache.remove(cacheKey);
        }

        // Trigger async lookup and return conservative default
        lookupBlockAsync(block, api, cacheKey);

        // Conservative default: assume not natural (player-placed)
        // This prevents exploits while cache is being populated
        return false;
    }

    private static void lookupBlockAsync(Block block, CoreProtectAPI api, String cacheKey) {
        CompletableFuture.runAsync(() -> {
            try {
                List<String[]> list = api.blockLookup(block, LOOKUP_TIME_SECONDS);
                boolean isNatural = (list == null || list.isEmpty());
                naturalBlockCache.put(cacheKey, new CachedResult(isNatural));
            } catch (Exception e) {
                // On error, cache as not natural (conservative)
                naturalBlockCache.put(cacheKey, new CachedResult(false));
            }
        });
    }

    /**
     * Invalidate cache for a specific block location.
     * Call this when a block is placed or broken to ensure fresh lookups.
     */
    public static void invalidateCache(Block block) {
        naturalBlockCache.remove(getCacheKey(block));
    }

    /**
     * Clear the entire natural block cache.
     */
    public static void clearCache() {
        naturalBlockCache.clear();
    }

    public void addPickup(Location location, ItemStack itemStack, Player player) {
        if (SCore.hasCoreProtect) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

            if (plugin != null && !plugin.isEnabled()) return;

            // Check that CoreProtect is loaded
            if (!(plugin instanceof CoreProtect)) {
                return;
            }

            CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
            if (!CoreProtect.isEnabled()) {
                return;
            }

            // Check that a compatible version of the API is loaded
            if (CoreProtect.APIVersion() < 9) {
                return;
            }

            if (!(Config.getConfig(location.getWorld())).ITEM_PICKUPS)
                return;

            if (itemStack == null)
                return;
            String loggingItemId = player.getName().toLowerCase(Locale.ROOT) + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            int itemId = new Register().getItem(loggingItemId);
            List<ItemStack> list = (List<ItemStack>) ConfigHandler.itemsPickup.getOrDefault(loggingItemId, new ArrayList());
            list.add(itemStack.clone());
            ConfigHandler.itemsPickup.put(loggingItemId, list);
            int time = (int) (System.currentTimeMillis() / 1000L) + 1;
            new Register().addItemTransaction(player, location.clone(), time, itemId);
        }

    }

    static class Register extends Queue {
        public Register() {

        }

        public static int getItem(String loggingItemId) {
            return getItemId(loggingItemId);
        }

        public void addItemTransaction(Player player, Location location, int time, int itemId) {
            Queue.queueItemTransaction(player.getName(), location.clone(), time, 0, itemId);
        }
    }

}
