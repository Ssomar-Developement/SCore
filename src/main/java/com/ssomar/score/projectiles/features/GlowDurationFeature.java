package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.IntegerFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;

import java.util.Optional;

public class GlowDurationFeature extends IntegerFeature implements SProjectileFeatureInterface {


    public GlowDurationFeature(FeatureParentInterface parent) {
        super(parent, Optional.of(-1), FeatureSettingsSCore.glowDuration);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof SpectralArrow) {
            SpectralArrow aA = (SpectralArrow) e;
            if (getValue().isPresent() && getValue().get() != -1)
                aA.setGlowingTicks(getValue().get());
        }
    }

    @Override
    public GlowDurationFeature clone(FeatureParentInterface newParent) {
        GlowDurationFeature clone = new GlowDurationFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}