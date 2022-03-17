package com.ssomar.score.projectiles.types;


import com.ssomar.score.SCore;
import com.ssomar.score.projectiles.features.*;
import com.ssomar.score.projectiles.features.Particles.ParticlesFeature;
import com.ssomar.score.projectiles.features.PassengerFeature;

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
        /* Color feature not available in 1.12 (for arrow) */
        if(!SCore.is1v12())
        proj = new ColorFeature(proj);
        proj = new ParticlesFeature(proj);
        proj = new PassengerFeature(proj);
        proj = new RemoveWhenHitBlockFeature(proj);
        return proj;
    }

}
