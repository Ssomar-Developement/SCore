package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomLingering extends CustomProjectile {

    CustomProjectile customLingering;

    public CustomLingering(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customLingering = new CustomNameFeature(this);
        customLingering = new InvisibleFeature(customLingering);
        customLingering = new GlowingFeature(customLingering);
        customLingering = new BounceFeature(customLingering);
        customLingering = new GravityFeature(customLingering);
        customLingering = new DespawnFeature(customLingering);
        customLingering = new VelocityFeature(customLingering);
        customLingering = new SilentFeature(customLingering);
        customLingering = new ColorFeature(customLingering);
        customLingering = new ParticlesFeature(customLingering);
        customLingering = new PotionEffectsFeature(customLingering);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customLingering;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
