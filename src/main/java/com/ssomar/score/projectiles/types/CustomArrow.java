package com.ssomar.score.projectiles.types;


import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class CustomArrow extends SProjectiles {


    public CustomArrow(String id, File file) {
        super(id, file);
    }

    public CustomArrow(String id, File file, boolean showError) {
        super(id, file, showError);
    }

    @Override
    public CustomProjectile setup(CustomProjectile proj) {
        proj = new CustomNameFeature(this);
        proj = new InvisibleFeature(proj);
        proj = new PickupFeature(proj);
        proj = new GlowingFeature(proj);
        proj = new CriticalFeature(proj);
        proj = new BounceFeature(proj);
        proj = new GravityFeature(proj);
        proj = new DamageFeature(proj);
        proj = new KnockbackStrengthFeature(proj);
        proj = new PierceLevelFeature(proj);
        proj = new DespawnFeature(proj);
        proj = new VelocityFeature(proj);
        proj = new SilentFeature(proj);
        proj = new ColorFeature(proj);
        proj = new ParticlesFeature(proj);
        return proj;
    }

}
