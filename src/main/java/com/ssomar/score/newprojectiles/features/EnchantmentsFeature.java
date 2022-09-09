package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.score.features.custom.enchantments.group.EnchantmentsGroupFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentsFeature extends EnchantmentsGroupFeature implements SProjectileFeatureInterface {


    public EnchantmentsFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, notSaveIfNoValue);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Trident) {
            Trident t = (Trident) e;
            try {
                ItemStack item = t.getItem();
                ItemMeta meta = item.getItemMeta();
                for (EnchantmentWithLevelFeature enchant : getValue().getEnchantments().values()) {
                    meta.addEnchant(enchant.getEnchantment().getValue().get(), enchant.getLevel().getValue().get(), true);
                }
                item.setItemMeta(meta);
                t.setItem(item);
            } catch (NoSuchMethodError ignored) {
            }
        }
    }

    @Override
    public EnchantmentsFeature clone(FeatureParentInterface newParent) {
        EnchantmentsFeature clone = new EnchantmentsFeature(newParent, false);
        clone.setEnchantments(getEnchantments());
        return clone;
    }

}
