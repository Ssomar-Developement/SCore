package com.ssomar.score.projectiles.types;

import com.ssomar.score.SCore;
import com.ssomar.score.projectiles.features.*;

import java.io.File;

public class CustomTrident extends SProjectiles {


    public CustomTrident(String id, File file) {
        super(id, file);
    }

    public CustomTrident(String id, File file, boolean showError) {
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
        if (!SCore.is1v13Less()) {
            proj = new PierceLevelFeature(proj);
            proj = new CriticalFeature(proj);
            /* No damage for trident, idk why it works only for arrow */
            proj = new KnockbackStrengthFeature(proj);
            proj = new PickupFeature(proj);
            proj = new RemoveWhenHitBlockFeature(proj);
            proj = new VisualItemFeature(proj);
        }
        if (!SCore.is1v12Less()) {
            proj = new EnchantmentsFeature(proj);
        }
        /* Particle feature not available in 1.11 */
        if (!SCore.is1v11Less())
            proj = new ParticlesFeature(proj);
        return proj;
    }
}
