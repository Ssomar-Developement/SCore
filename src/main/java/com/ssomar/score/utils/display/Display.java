package com.ssomar.score.utils.display;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class Display {
    public static final String PREFIX = StringConverter.coloredString("&z");

    private static final Map<Integer, List<DisplayModule>> REGISTERED_MODULES = new TreeMap<>();

    private static final NamespacedKey FINALIZE_KEY = new NamespacedKey(SCore.plugin, "finalized");

    public static DisplayRequestResult display(@NotNull ItemStack itemStack) {
        return display(itemStack, null);
    }

    public static boolean isSomethingToModify(){
        for (List<DisplayModule> modules : REGISTERED_MODULES.values()) {
            for (DisplayModule module : modules) {
                //System.out.println("Display.isSomethingToModify: module.getLoadedIDs() called "+module.getLoadedIDs());
                if(!module.getLoadedIDs().isEmpty()) return true;
            }
        }
        return false;
    }

    public static DisplayRequestResult display(@NotNull ItemStack itemStack, @Nullable Player player) {
        // System.out.println("Display.display: display(itemStack, player) called 0");
        if (itemStack.getType() == Material.AIR || !itemStack.hasItemMeta())
            return new DisplayRequestResult(itemStack, DisplayResult.NOT_MODIFIED);
        //revert(itemStack);

        ItemStack original = itemStack.clone();
        /* player.getOpenInventory() can be null when it is a custom GUI https://discord.com/channels/701066025516531753/1157683507699650560 */
        Inventory inventory = (player == null) ? null : player.getInventory();
        boolean inInventory = (inventory != null  && !inventory.isEmpty() && inventory.contains(original));
        /* TODO boolean inGui = (player != null && GUIDetectionManager.hasGUIOpen(player)); */
        boolean inGui = false;
        DisplayProperties properties = new DisplayProperties(inInventory, inGui, original);
        boolean modified = false;
        for (List<DisplayModule> modules : REGISTERED_MODULES.values()) {
            for (DisplayModule module : modules) {
                modified = module.display(itemStack) || modified;
                // System.out.println("Display.display: module.display(itemStack, varargs) called 1");
                if (player != null) {
                    // System.out.println("Display.display: module.display(itemStack, varargs) called 2");
                    modified = module.display(itemStack, player) || modified;
                    modified = module.display(itemStack, player, properties) || modified;
                    // System.out.println("Display.display: module.display(itemStack, varargs) called 3");
                }
            }
        }
        DisplayResult result = modified ? DisplayResult.MODIFIED : DisplayResult.NOT_MODIFIED;
        return new DisplayRequestResult(itemStack, result);
    }

    public static ItemStack displayAndFinalize(@NotNull ItemStack itemStack) {
        return finalize(display(itemStack, null).getItemStack());
    }

    public static ItemStack displayAndFinalize(@NotNull ItemStack itemStack, @Nullable Player player) {
        return finalize(display(itemStack, player).getItemStack());
    }

    public static ItemStack revert(@NotNull ItemStack itemStack) {
        if (isFinalized(itemStack))
            unfinalize(itemStack);
        /* FastItemStack fast = FastItemStack.wrap(itemStack); */
        ItemStack fast = itemStack;
        ItemMeta meta = fast.getItemMeta();
        if(!meta.hasLore()) return itemStack;
        List<String> lore = meta.getLore();
        if (!lore.isEmpty() && lore.removeIf(line -> line.startsWith(StringConverter.coloredString(PREFIX)))) {
            meta.setLore(lore);
            fast.setItemMeta(meta);
        }
        for (List<DisplayModule> modules : REGISTERED_MODULES.values()) {
            for (DisplayModule module : modules)
                module.revert(itemStack);
        }
        return itemStack;
    }

    public static ItemStack finalize(@NotNull ItemStack itemStack) {
        if (itemStack.getType().getMaxStackSize() > 1)
            return itemStack;
        /* FastItemStack.wrap(itemStack) */
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer()
                .set(FINALIZE_KEY, PersistentDataType.INTEGER, Integer.valueOf(1));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack unfinalize(@NotNull ItemStack itemStack) {
       /*  FastItemStack.wrap(itemStack) */
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer()
                .remove(FINALIZE_KEY);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static boolean isFinalized(@NotNull ItemStack itemStack) {
        return /* FastItemStack.wrap(itemStack) */ itemStack.getItemMeta()
                .getPersistentDataContainer()
                .has(FINALIZE_KEY, PersistentDataType.INTEGER);
    }

    public static void registerDisplayModule(@NotNull DisplayModule module) {
        List<DisplayModule> modules = REGISTERED_MODULES.getOrDefault(
                Integer.valueOf(module.getWeight()), new ArrayList<>());
        modules.removeIf(it -> it.getPluginName().equalsIgnoreCase(module.getPluginName()));
        modules.add(module);
        REGISTERED_MODULES.put(Integer.valueOf(module.getWeight()), modules);
    }

    private Display() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}