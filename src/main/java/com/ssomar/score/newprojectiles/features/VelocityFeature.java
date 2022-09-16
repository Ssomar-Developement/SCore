package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Optional;

public class VelocityFeature extends DoubleFeature implements SProjectileFeatureInterface {

    public VelocityFeature(FeatureParentInterface parent) {
        super(parent, "velocity", Optional.empty(), "Velocity", new String[]{}, FixedMaterial.getMaterial(Arrays.asList("FIREWORK_ROCKET", "ELYTRA")), false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        Vector v = e.getVelocity();
        if (!SCore.is1v11Less() && e instanceof ShulkerBullet) v = launcher.getEyeLocation().getDirection();
        else if (v.getX() == 0 && v.getY() == 0 && v.getZ() == 0) v = launcher.getEyeLocation().getDirection();
        if (getValue().isPresent() && getValue().get() != 1)
            v = v.multiply(getValue().get());
        e.setVelocity(v);
    }

    @Override
    public VelocityFeature clone(FeatureParentInterface newParent) {
        VelocityFeature clone = new VelocityFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }

}