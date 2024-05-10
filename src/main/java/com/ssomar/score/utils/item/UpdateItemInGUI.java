package com.ssomar.score.utils.item;

import com.ssomar.score.menu.GUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UpdateItemInGUI {

    public static void updateItemInGUI(GUI gui, String editorName, String name, List<String> lore, Material material) {
        /* Bug item no update idk why */
        ItemStack clone = new ItemStack(material);
        int slot = gui.getSlotByName(editorName);
        if(slot == -1) return;
        gui.getInv().setItem(slot, null);
        ItemMeta meta2 = clone.getItemMeta();
        meta2.setDisplayName(name);
        meta2.setLore(lore);
        clone.setItemMeta(meta2);
        gui.getInv().setItem(slot, clone);
    }
}
