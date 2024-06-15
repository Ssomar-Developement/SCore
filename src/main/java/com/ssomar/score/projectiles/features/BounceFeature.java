package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class BounceFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public BounceFeature(FeatureParentInterface parent) {
        super(parent,  false, FeatureSettingsSCore.bounce, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Projectile) {
            ((Projectile) e).setBounce(getValue());
        }
    }

    @Override
    public BounceFeature clone(FeatureParentInterface newParent) {
        BounceFeature clone = new BounceFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
