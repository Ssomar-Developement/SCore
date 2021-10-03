package com.ssomar.score.projectiles.types;

import com.ssomar.score.projectiles.features.*;
import com.ssomar.score.projectiles.features.Particles.ParticlesFeature;

import java.io.File;

public class CustomEgg extends SProjectiles {


    public CustomEgg(String id, File file) {
        super(id, file);
    }

    public CustomEgg(String id, File file, boolean showError) {
        super(id, file, showError);
    }

    @Override
    public CustomProjectile setup(CustomProjectile proj) {
        proj = new CustomNameFeature(proj);
        proj = new InvisibleFeature(proj);
        proj = new GlowingFeature(proj);
        proj = new BounceFeature(proj);
        proj = new GravityFeature(proj);
        proj = new DespawnFeature(proj);
        proj = new VelocityFeature(proj);
        proj = new SilentFeature(proj);
        proj = new ParticlesFeature(proj);
        proj = new VisualItemFeature(proj);
        return proj;
    }

}
