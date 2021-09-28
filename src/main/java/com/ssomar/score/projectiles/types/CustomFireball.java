package com.ssomar.score.projectiles.types;

import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomFireball extends SProjectiles {

    public CustomFireball(String id, FileConfiguration projConfig) {
        super(id, projConfig);
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
        return proj;
    }
}
