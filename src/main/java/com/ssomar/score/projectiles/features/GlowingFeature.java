package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GlowingFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public GlowingFeature(FeatureParentInterface parent) {
        super(parent, false, FeatureSettingsSCore.glowing, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        // doesnt work in 1.8
        if(!SCore.is1v11Less()) e.setGlowing(getValue());
    }

    @Override
    public GlowingFeature clone(FeatureParentInterface newParent) {
        GlowingFeature clone = new GlowingFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
