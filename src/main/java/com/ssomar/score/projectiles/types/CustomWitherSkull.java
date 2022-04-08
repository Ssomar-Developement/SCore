package com.ssomar.score.projectiles.types;

import com.ssomar.score.projectiles.features.*;
import com.ssomar.score.projectiles.features.ParticlesFeature;

import java.io.File;

public class CustomWitherSkull extends SProjectiles {


    public CustomWitherSkull(String id, File file) {
        super(id, file);
    }

    public CustomWitherSkull(String id, File file, boolean showError) {
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
        proj = new RadiusFeature(proj);
        proj = new IncendiaryFeature(proj);
        proj = new ChargedFeature(proj);
        proj = new RemoveWhenHitBlockFeature(proj);
        return proj;
    }
}
