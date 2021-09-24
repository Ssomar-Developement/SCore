package com.ssomar.score.projectiles.types;


import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomArrow extends CustomProjectile {

    CustomProjectile customArrow;

    public CustomArrow(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customArrow = new CustomNameFeature(this);
        customArrow = new InvisibleFeature(customArrow);
        customArrow = new PickupFeature(customArrow);
        customArrow = new GlowingFeature(customArrow);
        customArrow = new CriticalFeature(customArrow);
        customArrow = new BounceFeature(customArrow);
        customArrow = new GravityFeature(customArrow);
        customArrow = new DamageFeature(customArrow);
        customArrow = new KnockbackStrengthFeature(customArrow);
        customArrow = new PierceLevelFeature(customArrow);
        customArrow = new DespawnFeature(customArrow);
        customArrow = new VelocityFeature(customArrow);
        customArrow = new SilentFeature(customArrow);
        customArrow = new ColorFeature(customArrow);
        customArrow = new ParticlesFeature(customArrow);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customArrow;
    }
}
