package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.DoubleFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

// Not available to projectile
public class ScaleFeature extends DoubleFeature implements SProjectileFeatureInterface {

    public ScaleFeature(FeatureParentInterface parent) {
        super(parent,  Optional.of(1.0), FeatureSettingsSCore.scale);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        Runnable r = () -> {
           /*  SsomarDev.testMsg("isAttributable ? "+(e instanceof Attributable), true);
            if (e instanceof Attributable && getValue().isPresent()) {
                Attributable livingEntity = (Attributable) e;
                SsomarDev.testMsg("ScaleFeature transformTheProjectile >> "+getValue().get(), true);
                livingEntity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(getValue().get());
            } */
        };
        SCore.schedulerHook.runTask(r, 2);
    }

    @Override
    public ScaleFeature clone(FeatureParentInterface newParent) {
        ScaleFeature clone = new ScaleFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }

}
