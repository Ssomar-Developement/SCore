package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomWitherSkull extends CustomProjectile {

    CustomProjectile customWitherSkull;

    public CustomWitherSkull(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customWitherSkull = new CustomNameFeature(this);
        customWitherSkull = new InvisibleFeature(customWitherSkull);
        customWitherSkull = new GlowingFeature(customWitherSkull);
        customWitherSkull = new BounceFeature(customWitherSkull);
        customWitherSkull = new GravityFeature(customWitherSkull);
        customWitherSkull = new DespawnFeature(customWitherSkull);
        customWitherSkull = new VelocityFeature(customWitherSkull);
        customWitherSkull = new SilentFeature(customWitherSkull);
        customWitherSkull = new ParticlesFeature(customWitherSkull);
        customWitherSkull = new RadiusFeature(customWitherSkull);
        customWitherSkull = new IncendiaryFeature(customWitherSkull);
        customWitherSkull = new ChargedFeature(customWitherSkull);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customWitherSkull;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
