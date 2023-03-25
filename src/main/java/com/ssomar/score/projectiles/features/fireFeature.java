package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class fireFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public fireFeature(FeatureParentInterface parent) {
        super(parent, "visualFire", false, "visualFire", new String[]{}, Material.FLINT_AND_STEEL, false, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Projectile) {
            ((Projectile) e).setVisualFire(getValue());
        }
    }

    @Override
    public fireFeature clone(FeatureParentInterface newParent) {
        fireFeature clone = new fireFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
