package com.ssomar.score.projectiles.types;

import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomTrident extends SProjectiles {

    public CustomTrident(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public CustomProjectile setup(CustomProjectile proj) {
        proj = new CustomNameFeature(proj);
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
        proj = new ParticlesFeature(proj);
        proj = new VisualItemFeature(proj);
        proj = new EnchantmentsFeature(proj);
        return proj;
    }
}
