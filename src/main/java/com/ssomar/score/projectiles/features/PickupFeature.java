package com.ssomar.score.projectiles.features;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class PickupFeature extends com.ssomar.score.features.types.PickupFeature implements SProjectileFeatureInterface {

    public PickupFeature(FeatureParentInterface parent) {
        super(parent, "pickupStatus", Optional.of(AbstractArrow.PickupStatus.ALLOWED), "Pickup status", new String[]{}, FixedMaterial.getMaterial(Arrays.asList("LEAD", "LEASH")), false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof AbstractArrow)
            ((AbstractArrow) e).setPickupStatus(getValue().get());
    }

    @Override
    public PickupFeature clone(FeatureParentInterface newParent) {
        PickupFeature clone = new PickupFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }

}
