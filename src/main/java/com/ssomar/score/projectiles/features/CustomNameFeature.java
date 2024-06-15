package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.ColoredStringFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CustomNameFeature extends ColoredStringFeature implements SProjectileFeatureInterface {

    public CustomNameFeature(FeatureParentInterface parent) {
        super(parent,  Optional.of("Default name"), FeatureSettingsSCore.customName, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getColoredValue().isPresent()) e.setCustomName(getColoredValue().get());
    }

    @Override
    public CustomNameFeature clone(FeatureParentInterface newParent) {
        CustomNameFeature clone = new  CustomNameFeature(newParent);
        clone.setValue(getValue().orElse(null));
        return clone;
    }
}
