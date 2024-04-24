package com.ssomar.score.utils.item;

import com.ssomar.score.SCore;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MakeItemGlow {

    public static ItemStack makeGlow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta = makeGlow(meta);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemMeta makeGlow(ItemMeta meta) {
        if(SCore.is1v20v5Plus()) {
            meta.setEnchantmentGlintOverride(true);
        }
        else {
            meta.addEnchant(Enchantment.getByName("PROTECTION_FALL"), 6, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return meta;
    }
}
