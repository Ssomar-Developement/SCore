package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;

public class ChargedFeature extends BooleanFeature implements SProjectileFeatureInterface{

    public ChargedFeature(FeatureParentInterface parent) {
        super(parent, "charger", false, "Charged", new String[]{""}, Material.NETHER_STAR, false, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof WitherSkull) {
            ((WitherSkull) e).setCharged(getValue());
        }
    }

}
