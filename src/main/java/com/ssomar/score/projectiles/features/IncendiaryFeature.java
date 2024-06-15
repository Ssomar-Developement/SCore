package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;

public class IncendiaryFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public IncendiaryFeature(FeatureParentInterface parent) {
        super(parent,  false, FeatureSettingsSCore.incendiary, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Explosive) {
            Explosive fireball = (Explosive) e;
            fireball.setIsIncendiary(getValue());
        }
    }

    @Override
    public IncendiaryFeature clone(FeatureParentInterface newParent) {
        IncendiaryFeature clone = new IncendiaryFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}