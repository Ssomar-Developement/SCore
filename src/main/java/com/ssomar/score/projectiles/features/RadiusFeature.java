package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class RadiusFeature extends DoubleFeature implements SProjectileFeatureInterface {

    public RadiusFeature(FeatureParentInterface parent) {
        super(parent, "radius", Optional.empty(), "Radius", new String[]{}, FixedMaterial.getMaterial(Arrays.asList("HEART_OF_THE_SEA", "WEB")), false);
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