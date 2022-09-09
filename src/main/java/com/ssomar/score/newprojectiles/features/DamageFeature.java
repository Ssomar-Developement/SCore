package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.DoubleFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DamageFeature extends DoubleFeature implements SProjectileFeatureInterface {

    public DamageFeature(FeatureParentInterface parent) {
        super(parent, "damage", Optional.of(-1.0), "Damage", new String[]{"&7&o-1 for vanilla damage"}, Material.DIAMOND_SWORD, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (getValue().get() != -1) {
                aA.setDamage(getValue().get());
            }
        }
    }

    @Override
    public DamageFeature clone(FeatureParentInterface newParent) {
        DamageFeature clone = new DamageFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
