package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.IntegerFeature;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class KnockbackStrengthFeature extends IntegerFeature implements SProjectileFeatureInterface {

    public KnockbackStrengthFeature(FeatureParentInterface parent) {
        super(parent, "knockbackStrength", Optional.of(-1), "Knockback Strength", new String[]{}, Material.CHAINMAIL_CHESTPLATE, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (getValue().isPresent() && getValue().get() != -1)
                aA.setKnockbackStrength(getValue().get());
        }
    }

}
