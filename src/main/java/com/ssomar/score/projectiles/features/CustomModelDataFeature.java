package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.IntegerFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class CustomModelDataFeature extends IntegerFeature implements SProjectileFeatureInterface {

    public CustomModelDataFeature(FeatureParentInterface parent) {
        super(parent, Optional.empty(), FeatureSettingsSCore.customModelData);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof ThrowableProjectile) {
            ItemStack item = ((ThrowableProjectile)e).getItem();
            ItemMeta meta = item.getItemMeta();
            if (getValue().get() != -1) meta.setCustomModelData(getValue().get());
            item.setItemMeta(meta);
            ((ThrowableProjectile) e).setItem(item);
        }
    }

    @Override
    public CustomModelDataFeature clone(FeatureParentInterface newParent) {
        CustomModelDataFeature clone = new  CustomModelDataFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
