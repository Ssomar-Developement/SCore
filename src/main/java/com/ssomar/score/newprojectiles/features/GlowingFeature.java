package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GlowingFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public GlowingFeature(FeatureParentInterface parent) {
        super(parent, "glowing", false, "Glowing", new String[]{}, Material.BEACON, false, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        e.setGlowing(getValue());
    }

    @Override
    public GlowingFeature clone(FeatureParentInterface newParent) {
        GlowingFeature clone = new GlowingFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
