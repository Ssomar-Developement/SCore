package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomSnowball extends CustomProjectile {

    CustomProjectile customSnowball;

    public CustomSnowball(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customSnowball = new CustomNameFeature(this);
        customSnowball = new InvisibleFeature(customSnowball);
        customSnowball = new GlowingFeature(customSnowball);
        customSnowball = new BounceFeature(customSnowball);
        customSnowball = new GravityFeature(customSnowball);
        customSnowball = new DespawnFeature(customSnowball);
        customSnowball = new VelocityFeature(customSnowball);
        customSnowball = new SilentFeature(customSnowball);
        customSnowball = new ParticlesFeature(customSnowball);
        customSnowball = new VisualItemFeature(customSnowball);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customSnowball;
    }

}
