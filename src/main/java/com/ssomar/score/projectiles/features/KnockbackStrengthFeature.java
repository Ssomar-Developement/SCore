package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.IntegerFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;


public class KnockbackStrengthFeature extends IntegerFeature implements SProjectileFeatureInterface {

    public KnockbackStrengthFeature(FeatureParentInterface parent) {
        super(parent, Optional.of(-1), FeatureSettingsSCore.knockbackStrength);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (getValue().isPresent() && getValue().get() != -1) {
                SsomarDev.testMsg("knockback strength: " + getValue().get(), true);
                if(SCore.is1v21Plus()){
                    ItemStack item = new ItemStack(Material.BOW);
                    ItemMeta meta = item.getItemMeta();
                    meta.addEnchant(org.bukkit.enchantments.Enchantment.KNOCKBACK, getValue().get(), true);
                    item.setItemMeta(meta);
                    aA.setWeapon(item);
                }else {
                    aA.setKnockbackStrength(getValue().get());
                }
            }
        }
    }

    @Override
    public KnockbackStrengthFeature clone(FeatureParentInterface newParent) {
        KnockbackStrengthFeature clone = new KnockbackStrengthFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }

}
