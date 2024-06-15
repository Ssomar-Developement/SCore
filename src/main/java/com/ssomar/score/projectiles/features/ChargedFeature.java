package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;

public class ChargedFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public ChargedFeature(FeatureParentInterface parent) {
        super(parent,  false, FeatureSettingsSCore.charged, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof WitherSkull) {
            ((WitherSkull) e).setCharged(getValue());
        }
    }

    @Override
    public ChargedFeature clone(FeatureParentInterface newParent) {
        ChargedFeature clone = new  ChargedFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }

}
