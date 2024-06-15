package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GravityFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public GravityFeature(FeatureParentInterface parent) {
        super(parent,  true, FeatureSettingsSCore.gravity, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        // doesnt work in 1.8
        if(!SCore.is1v11Less()) e.setGravity(getValue());
    }

    @Override
    public GravityFeature clone(FeatureParentInterface newParent) {
        GravityFeature clone = new GravityFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
