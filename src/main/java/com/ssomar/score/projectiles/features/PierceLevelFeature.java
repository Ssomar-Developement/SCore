package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.IntegerFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PierceLevelFeature extends IntegerFeature implements SProjectileFeatureInterface {


    public PierceLevelFeature(FeatureParentInterface parent) {
        super(parent, "pierceLevel", Optional.of(-1), "Pierce level", new String[]{}, Material.TIPPED_ARROW, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (getValue().isPresent() && getValue().get() != -1)
                aA.setPierceLevel(getValue().get());
        }
    }

    @Override
    public PierceLevelFeature clone(FeatureParentInterface newParent) {
        PierceLevelFeature clone = new PierceLevelFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}