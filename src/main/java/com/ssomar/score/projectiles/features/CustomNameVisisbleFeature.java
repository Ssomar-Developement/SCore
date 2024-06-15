package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CustomNameVisisbleFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public CustomNameVisisbleFeature(FeatureParentInterface parent) {
        super(parent, false, FeatureSettingsSCore.customNameVisible, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        e.setCustomNameVisible(getValue());
    }

    @Override
    public CustomNameVisisbleFeature clone(FeatureParentInterface newParent) {
        CustomNameVisisbleFeature clone = new  CustomNameVisisbleFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
