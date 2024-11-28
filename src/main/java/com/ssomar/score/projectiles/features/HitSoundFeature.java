package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.SoundFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class HitSoundFeature extends SoundFeature implements SProjectileFeatureInterface {


    public HitSoundFeature(FeatureParentInterface parent) {
        super(parent, Optional.empty(), FeatureSettingsSCore.hitSound);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof AbstractArrow && SCore.isPaper()) {
            AbstractArrow aA = (AbstractArrow) e;
            if (getValue().isPresent() && getValue().isPresent()) {
                aA.setHitSound(getValue().get());
            }
        }
    }

    @Override
    public HitSoundFeature clone(FeatureParentInterface newParent) {
        HitSoundFeature clone = new HitSoundFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}