package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.MaterialFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class VisualItemFeature extends MaterialFeature implements SProjectileFeatureInterface {

    public VisualItemFeature(FeatureParentInterface parent) {
        super(parent, "visualItem", Optional.of(Material.BARRIER), "Visual Item", new String[]{}, Material.ITEM_FRAME, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof ThrowableProjectile && !getValue().get().equals(Material.BARRIER)) {
            ItemStack item = new ItemStack(getValue().get());
            ((ThrowableProjectile) e).setItem(item);
        }
    }

    @Override
    public VisualItemFeature clone(FeatureParentInterface newParent) {
        VisualItemFeature clone = new VisualItemFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
