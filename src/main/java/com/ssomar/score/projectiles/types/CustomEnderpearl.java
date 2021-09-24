package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomEnderpearl extends CustomProjectile {

    CustomProjectile customEnderpearl;

    public CustomEnderpearl(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customEnderpearl = new CustomNameFeature(this);
        customEnderpearl = new InvisibleFeature(customEnderpearl);
        customEnderpearl = new GlowingFeature(customEnderpearl);
        customEnderpearl = new BounceFeature(customEnderpearl);
        customEnderpearl = new GravityFeature(customEnderpearl);
        customEnderpearl = new DespawnFeature(customEnderpearl);
        customEnderpearl = new VelocityFeature(customEnderpearl);
        customEnderpearl = new SilentFeature(customEnderpearl);
        customEnderpearl = new ParticlesFeature(customEnderpearl);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customEnderpearl;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
