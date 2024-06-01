package com.ssomar.score.utils.safebreak;

import com.ssomar.executableblocks.api.ExecutableBlocksAPI;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.events.BlockBreakEventExtension;
import com.ssomar.score.usedapi.*;
import dev.rosewood.roseloot.RoseLoot;
import dev.rosewood.roseloot.loot.LootResult;
import dev.rosewood.roseloot.loot.context.LootContext;
import dev.rosewood.roseloot.loot.context.LootContextParams;
import dev.rosewood.roseloot.loot.table.LootTableTypes;
import dev.rosewood.roseloot.manager.LootTableManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SafeBreak {

    private static final boolean DEBUG = false;

    /* return false its verifSafeBreak is false */
    public static boolean breakBlockWithEvent(final Block block, @Nullable final UUID playerUUID, int slot, boolean drop, boolean generateBreakEvent, boolean verifSafeBreak) {

        SsomarDev.testMsg("DEBUG SAFE BREAK 1", DEBUG);
        if (playerUUID == null) {
            if (breakEB(null, block, drop)) return true;
            block.breakNaturally();
            return true;
        }
        SsomarDev.testMsg("DEBUG SAFE BREAK 1.5", DEBUG);

        Player player = Bukkit.getServer().getPlayer(playerUUID);

        SsomarDev.testMsg("DEBUG SAFE BREAK 1.6 p: "+player, DEBUG);

        if(!(player != null && player.isOp())){
            if (verifSafeBreak && !verifSafeBreak(playerUUID, block)){
                SsomarDev.testMsg("DEBUG SAFE BREAK VERIFICATION BLOCKED ", DEBUG);
                return false;
            }
        }


        SsomarDev.testMsg("DEBUG SAFE BREAK 2", DEBUG);
        if (player != null) {
            SsomarDev.testMsg("DEBUG SAFE BREAK 3", DEBUG);
            boolean canceled = false;

            if(SCore.hasItemsAdder && ItemsAdderAPI.breakCustomBlock(block, player.getInventory().getItemInMainHand(), drop)) return true;

            if (generateBreakEvent) {
                SsomarDev.testMsg("DEBUG SAFE BREAK 4", DEBUG);
                BlockBreakEvent bbE = new BlockBreakEventExtension(block, player, true);
                bbE.setCancelled(false);
                /* */
                Bukkit.getPluginManager().callEvent(bbE);
                canceled = bbE.isCancelled();
                if(!SCore.is1v11Less()) drop = bbE.isDropItems() && drop;
            }

            if (!canceled) {
                if (breakEB(player, block, drop)) return true;

                breakRoseloot(player, block, drop);

                if (SCore.is1v11Less()) {
                    breakBlockNaturallyWith(block, Optional.ofNullable(player.getInventory().getItemInHand()), drop);
                } else {
                    if (slot != 40)
                        breakBlockNaturallyWith(block, Optional.ofNullable(player.getInventory().getItemInMainHand()), drop);
                    else
                        breakBlockNaturallyWith(block, Optional.ofNullable(player.getInventory().getItemInOffHand()), drop);
                }
            }
        } else {
            if(SCore.hasItemsAdder && ItemsAdderAPI.breakCustomBlock(block, null, drop)) return true;
            if (breakEB(null, block, drop)) return true;

            breakRoseloot(null, block, drop);

            //SsomarDev.testMsg("DEBUG SAFE BREAK 6");
            breakBlockNaturallyWith(block, Optional.empty(), drop);
        }

        return true;
    }

    public static void breakBlockNaturallyWith(Block block, Optional<ItemStack> itemStack, boolean drop) {
        //SsomarDev.testMsg("DEBUG SAFE BREAK 7", DEBUG);
        if (!SCore.is1v13Less() && block.getBlockData() instanceof Door) {
             SsomarDev.testMsg("DEBUG SAFE BREAK 8 BISECTED", DEBUG);
            Bisected b = (Bisected) block.getBlockData();
            if (b.getHalf().equals(Bisected.Half.BOTTOM)) {
                //SsomarDev.testMsg("DEBUG SAFE BREAK 9", DEBUG);
                if (itemStack.isPresent() && drop) block.breakNaturally(itemStack.get());
                else if (drop) block.breakNaturally();

                BukkitRunnable runnable3 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.AIR);
                        block.getRelative(BlockFace.UP).setType(Material.AIR);
                    }
                };
                SCore.schedulerHook.runTask(runnable3, 1);
            } else {
                if (itemStack.isPresent() && drop) block.breakNaturally(itemStack.get());
                else if (drop) block.breakNaturally();
                BukkitRunnable runnable3 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.getRelative(BlockFace.DOWN).setType(Material.AIR);
                        block.setType(Material.AIR);
                    }
                };
                SCore.schedulerHook.runTask(runnable3, 1);
            }
        } else if (block.getType().toString().toUpperCase().contains("DOOR") && !block.getType().toString().toUpperCase().contains("TRAPDOOR")) {
            if (block.getRelative(BlockFace.UP).getType().toString().toUpperCase().contains("DOOR")) {
                if (itemStack.isPresent() && drop) block.breakNaturally(itemStack.get());
                else if (drop) block.breakNaturally();
                BukkitRunnable runnable3 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.getRelative(BlockFace.UP).setType(Material.AIR);
                        block.setType(Material.AIR);
                    }
                };
                SCore.schedulerHook.runTask(runnable3, 1);
            } else {
                if (itemStack.isPresent() && drop) block.breakNaturally(itemStack.get());
                else if (drop) block.breakNaturally();
                BukkitRunnable runnable3 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.getRelative(BlockFace.DOWN).setType(Material.AIR);
                        block.setType(Material.AIR);
                    }
                };
                SCore.schedulerHook.runTask(runnable3, 1);
            }
        } else {
            if (itemStack.isPresent() && drop) block.breakNaturally(itemStack.get());
            else if (drop) block.breakNaturally();
            else block.setType(Material.AIR);
        }
    }

    public static boolean breakRoseloot(@Nullable Player player, Block block, boolean drop) {
        if(drop && SCore.hasRoseLoot) {
            try {
                LootContext context = LootContext.builder()
                        .put(LootContextParams.LOOTER, player)
                        .put(LootContextParams.LOOTED_BLOCK, block)
                        .put(LootContextParams.ORIGIN, block.getLocation())
                        .build();
                LootResult lootResult = RoseLoot.getInstance().getManager(LootTableManager.class).getLoot(LootTableTypes.BLOCK, context);
                List<ItemStack> itemsDropped = lootResult.getLootContents().getItems();
                SsomarDev.testMsg("DEBUG SAFE BREAK >> "+itemsDropped.size(), true);
                for (ItemStack itemStack : itemsDropped) {
                    block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                }
                // to have only the custom drop from RoseLoot
                // (REMOVED Roseloot + vanilla loot ) drop = false;
                return true;
            }catch (Exception e){
                SCore.plugin.getLogger().severe("RoseLoot hook is not working properly");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean breakEB(@Nullable Player player, Block block, boolean drop) {
       //SsomarDev.testMsg("DEBUG SAFE BREAK 10", DEBUG);

        if (SCore.hasExecutableBlocks) {
           // SsomarDev.testMsg("DEBUG SAFE BREAK has EB", DEBUG);
            Optional<ExecutableBlockPlaced> eBPOpt = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(block);
            if (eBPOpt.isPresent()) {
                //SsomarDev.testMsg("DEBUG SAFE BREAK has EB 2", DEBUG);
                eBPOpt.get().breakBlock(player, drop, null, ExecutableBlockPlaced.BreakMethod.CUSTOM);
                return true;
            }
        }
        return false;
    }

    public static boolean verifSafeBreak(@NotNull final UUID playerUUID, @NotNull Block block) {
        return verifSafeBreak(playerUUID, block.getLocation());
    }

    public static boolean verifSafeBreak(@NotNull final UUID playerUUID, @NotNull Location location) {

        Player player = Bukkit.getServer().getPlayer(playerUUID);

        if(player != null && (player.isOp() || player.hasPermission("*"))) return true;

        //SsomarDev.testMsg("DEBUG SAFE BREAK CDT 1");

        if (SCore.hasGriefPrevention)
            if (!GriefPreventionAPI.playerCanBreakClaimBlock(playerUUID, location)) return false;

        // SsomarDev.testMsg("DEBUG SAFE BREAK CDT 2");

        if (SCore.hasIridiumSkyblock)
            if (!IridiumSkyblockTool.playerCanBreakIslandBlock(playerUUID, location)) return false;

        if (SCore.hasSuperiorSkyblock2)
            if (!SuperiorSkyblockTool.playerCanBreakIslandBlock(playerUUID, location)) return false;

        if (SCore.hasBentoBox)
            if (!BentoBoxAPI.playerCanBreakIslandBlock(playerUUID, location)) return false;

        //SsomarDev.testMsg("DEBUG SAFE BREAK CDT 3");

        if (SCore.hasLands)
            if (!new LandsIntegrationAPI(SCore.plugin).playerCanBreakClaimBlock(playerUUID, location))
                return false;

        if(SCore.hasFactionsUUID)
            if(!new FactionsUUIDAPI().playerCanBreakClaimBlock(playerUUID, location)) return false;

        //SsomarDev.testMsg("DEBUG SAFE BREAK CDT 4");

        if (SCore.hasWorldGuard)
            if (!WorldGuardAPI.playerCanBreakInRegion(playerUUID, location)) return false;

        if (SCore.hasResidence)
            if (!ResidenceAPI.playerCanBreakClaimBlock(playerUUID, location)) return false;

        if(SCore.hasTowny)
            if(!TownyToolAPI.playerCanBreakBlock(playerUUID, location)) return false;

        if(SCore.hasProtectionStones)
            if(!ProtectionStonesAPI.playerCanBreakClaimBlock(playerUUID, location)) return false;

        return true;
    }

}
