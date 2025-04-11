package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.UncoloredStringFeature;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class ItemModelFeature extends UncoloredStringFeature implements SProjectileFeatureInterface {

    public ItemModelFeature(FeatureParentInterface parent) {
        super(parent, Optional.empty(), FeatureSettingsSCore.itemModel, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof ThrowableProjectile) {
            ItemStack item = ((ThrowableProjectile)e).getItem();
            ItemMeta meta = item.getItemMeta();
            if (SCore.is1v21v2Plus()) {
                meta.setItemModel(NamespacedKey.fromString(getValue().get()));
            }
            item.setItemMeta(meta);
            ((ThrowableProjectile) e).setItem(item);
        }
    }

    @Override
    public ItemModelFeature clone(FeatureParentInterface newParent) {
        ItemModelFeature clone = new ItemModelFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
