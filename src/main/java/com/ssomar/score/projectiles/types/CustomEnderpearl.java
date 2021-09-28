package com.ssomar.score.projectiles.types;

import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomEnderpearl extends SProjectiles {

    public CustomEnderpearl(String id, FileConfiguration projConfig) {
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
        return proj;
    }
}
