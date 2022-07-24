package com.ssomar.score.newprojectiles.features;

import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CriticalFeature extends BooleanFeature implements SProjectileFeatureInterface {

    public CriticalFeature(FeatureParentInterface parent) {
        super(parent, "critical", false, "Critical", new String[]{}, Material.DIAMOND_AXE, false, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            aA.setCritical(getValue());
        }
    }
}
