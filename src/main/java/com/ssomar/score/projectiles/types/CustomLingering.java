package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
public class CustomLingering extends CustomProjectile {

    CustomProjectile customLingering;
    String id;

    public CustomLingering(String id) {
        configGui = new SimpleGUI("EDITORE CURSTOM PROJECTILES ARROW", 5*9);
        this.id = id;
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
    public String getId() {
        return id;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
