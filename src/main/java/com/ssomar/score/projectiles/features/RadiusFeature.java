package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.DoubleFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;

import java.util.Optional;

public class RadiusFeature extends DoubleFeature implements SProjectileFeatureInterface {

    public RadiusFeature(FeatureParentInterface parent) {
        super(parent,  Optional.empty(), FeatureSettingsSCore.radius);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof Explosive) {
            Explosive fireball = (Explosive) e;
            fireball.setYield(getValue().get().floatValue());
        }
    }

    @Override
    public RadiusFeature clone(FeatureParentInterface newParent) {
        RadiusFeature clone = new RadiusFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}