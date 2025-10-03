package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

import static com.ssomar.score.utils.safebreak.SafeBreak.breakEB;

public class ItemsAdderAPI {

    public static boolean breakCustomBlock(Block block, ItemStack item, boolean drop) {
        SsomarDev.testMsg(ChatColor.GOLD+"[#s0003] breakCustomBlock() method call from ItemsAdderAPI.java", true);
        if (SCore.hasItemsAdder && block != null && !block.isEmpty()) {
            // https://discord.com/channels/701066025516531753/1386807735009677493 remove for custom IA build
            if (SCore.hasClass("dev.lone.itemsadder.api.CustomBlock")) {
                SsomarDev.testMsg("[#s0004] Attempted to break ItemsAdder block", true);
                CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
                //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+(customBlock != null), true);
                if (customBlock != null) {
                    SsomarDev.testMsg("[#s0005] Custom Block is not null", true);
                    //SsomarDev.testMsg("ITEM ADDER REMOVED", true);
                    if (drop) {
                        List<ItemStack> loots = customBlock.getLoot(item, false);
                        for (ItemStack loot : loots) {
                            block.getWorld().dropItemNaturally(block.getLocation(), loot);
                        }
                    }
                    customBlock.playBreakSound();
                    customBlock.playBreakEffect();
                    customBlock.playBreakParticles();
                    customBlock.remove();
                    Runnable runnable = new Runnable() {
                        public void run() {
                            block.setType(org.bukkit.Material.AIR);
                        }
                    };
                    SCore.schedulerHook.runTask(runnable, 1);
                    return true;
                }
            }
            ArmorStand armorStand;
            for (Entity e : block.getLocation().getWorld().getNearbyEntities(block.getLocation(), 0.5, 0.5, 0.5)) {
                if (e instanceof ArmorStand) {
                    SsomarDev.testMsg("[#s0006] Custom Block instanceof ArmorStanmd", true);
                    armorStand = (ArmorStand) e;
                    //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+armorStand.getCustomName(), true);
                    if (armorStand.getCustomName() != null && armorStand.getCustomName().equals("ItemsAdder_furniture")) {
                        SsomarDev.testMsg("[#s0007] Armorstand has a name and ArmorStand name is \"ItemsAdder_furniture\"", true);
                        CustomFurniture furniture = CustomFurniture.byAlreadySpawned(armorStand);
                        furniture.remove(drop);
                        // to patch an issue where BREAK doesn't remove the EB-ItemsAdder block
                        // its drop arg is set to false because when you use BREAK on an EB, it already drops the loot.
                        breakEB(null, block, false);
                        return true;
                    } else {
                        if (armorStand.getCustomName() == null) {
                            SsomarDev.testMsg("[#s0008] Armorstand has no name", true);
                        } else {
                            SsomarDev.testMsg("[#s0009] Armorstand is not IA; name is: " + armorStand.getCustomName(), true);
                        }
                    }
                }
            }

        }
        return false;

    }

    public static Optional<String> getCustomBlockID(Block block) {
        if (SCore.hasItemsAdder && block != null && !block.isEmpty()) {
            // https://discord.com/channels/701066025516531753/1386807735009677493 remove for custom IA build
            if (SCore.hasClass("dev.lone.itemsadder.api.CustomBlock")) {
                CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
                //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+(customBlock != null), true);
                if (customBlock != null) {
                    return Optional.of(customBlock.getId());
                }
            }
            ArmorStand armorStand;
            for (Entity e : block.getLocation().getWorld().getNearbyEntities(block.getLocation(), 0.5, 0.5, 0.5)) {
                if (e instanceof ArmorStand) {
                    armorStand = (ArmorStand) e;
                    if (armorStand.getCustomName() != null && armorStand.getCustomName().equals("ItemsAdder_furniture")) {
                        return Optional.of(CustomFurniture.byAlreadySpawned(armorStand).getId());
                    }
                }
            }

        }
        return Optional.empty();

    }

    public static Optional<String> getCustomItemID(ItemStack item) {
        if (SCore.hasItemsAdder && item != null) {
            CustomStack customStack = CustomStack.byItemStack(item);
            //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+(customBlock != null), true);
            if (customStack != null) {
                return Optional.of(customStack.getId());
            }
        }
        return Optional.empty();

    }

    public static boolean isCustomBlock(Block block) {
        return getCustomBlockID(block).isPresent();
    }


    public static boolean isCustomItem(ItemStack item) {
        return getCustomItemID(item).isPresent();
    }

    public static boolean placeItemAdder(Location location, String id) {
        try {
            CustomBlock customBlock = CustomBlock.getInstance(id);
            if (customBlock != null) {
                //SsomarDev.testMsg("placeItemsAdder Block: " + id, DEBUG);
                customBlock.place(location);
                return true;
            }
        } catch (Exception | Error e) {
            try {
                //SsomarDev.testMsg("placeItemsAdder is Furniture " + id, DEBUG);
                CustomFurniture.spawnPreciseNonSolid(id, location);
                return true;
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
