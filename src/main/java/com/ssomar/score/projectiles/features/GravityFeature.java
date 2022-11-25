package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GravityFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public GravityFeature(FeatureParentInterface parent) {
        super(parent, "gravity", true, "Gravity", new String[]{}, FixedMaterial.getMaterial(Arrays.asList("ELYTRA", "FEATHER")), false, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        e.setGravity(getValue());
    }

    @Override
    public GravityFeature clone(FeatureParentInterface newParent) {
        GravityFeature clone = new GravityFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
