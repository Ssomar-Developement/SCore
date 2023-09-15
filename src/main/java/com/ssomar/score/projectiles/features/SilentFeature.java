package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SilentFeature extends BooleanFeature implements SProjectileFeatureInterface {


    public SilentFeature(FeatureParentInterface parent) {
        super(parent, "silent", false, "Silent", new String[]{}, FixedMaterial.getMaterial(Arrays.asList("BELL", "JUKEBOW")), false, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        // doesnt work in 1.8
        if(!SCore.is1v11Less()) e.setSilent(getValue());
    }

    @Override
    public SilentFeature clone(FeatureParentInterface newParent) {
        SilentFeature clone = new SilentFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
