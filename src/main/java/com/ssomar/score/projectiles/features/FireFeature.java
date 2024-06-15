package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class FireFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public FireFeature(FeatureParentInterface parent) {
        super(parent,  false, FeatureSettingsSCore.visualFire, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Projectile) {
            ((Projectile) e).setVisualFire(getValue());
        }
    }

    @Override
    public FireFeature clone(FeatureParentInterface newParent) {
        FireFeature clone = new FireFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
