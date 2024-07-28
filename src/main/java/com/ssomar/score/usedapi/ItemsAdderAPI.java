package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;


import java.util.List;
import java.util.Optional;

public class ItemsAdderAPI {

    public static boolean breakCustomBlock(Block block, ItemStack item, boolean drop) {
        if (SCore.hasItemsAdder && block != null && !block.isEmpty()) {
            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
            //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+(customBlock != null), true);
            if (customBlock != null) {
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
            ArmorStand armorStand;
            for (Entity e : block.getLocation().getWorld().getNearbyEntities(block.getLocation(), 0.5, 0.5, 0.5)) {
                if (e instanceof ArmorStand) {
                    armorStand = (ArmorStand) e;
                    //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+armorStand.getCustomName(), true);
                    if (armorStand.getCustomName() != null && armorStand.getCustomName().equals("ItemsAdder_furniture")) {
                        CustomFurniture furniture = CustomFurniture.byAlreadySpawned(armorStand);
                        furniture.remove(drop);
                        return true;
                    }
                }
            }

        }
        return false;

    }

    public static Optional<String> getCustomBlockID(Block block) {
        if (SCore.hasItemsAdder && block != null && !block.isEmpty()) {
            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
            //SsomarDev.testMsg("ITEM ADDER DETECTED >> "+(customBlock != null), true);
            if (customBlock != null) {
                return Optional.of(customBlock.getId());
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
        } catch (Exception e) {
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
