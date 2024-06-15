package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.persistence.PersistentDataType;

public class RemoveWhenHitBlockFeature extends BooleanFeature implements SProjectileFeatureInterface {


    public RemoveWhenHitBlockFeature(FeatureParentInterface parent) {
        super(parent,  false, FeatureSettingsSCore.removeWhenHitBlock, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Projectile && getValue()) {
            ((Projectile) e).getPersistentDataContainer().set(new NamespacedKey(SCore.plugin, "remove_hit_block"), PersistentDataType.INTEGER, 1);
        }
    }

    @Override
    public RemoveWhenHitBlockFeature clone(FeatureParentInterface newParent) {
        RemoveWhenHitBlockFeature clone = new RemoveWhenHitBlockFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
