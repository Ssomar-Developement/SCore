package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CriticalFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public CriticalFeature(FeatureParentInterface parent) {
        super(parent, false, FeatureSettingsSCore.critical, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            aA.setCritical(getValue());
        }
    }

    @Override
    public CriticalFeature clone(FeatureParentInterface newParent) {
        CriticalFeature clone = new  CriticalFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
