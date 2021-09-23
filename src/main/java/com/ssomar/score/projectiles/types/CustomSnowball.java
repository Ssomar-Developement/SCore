package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;

public class CustomSnowball extends CustomProjectile {

    CustomProjectile customSnowball;
    String id;

    public CustomSnowball(String id) {
        configGui = new SimpleGUI("EDITORE CURSTOM PROJECTILES ARROW", 5*9);
        this.id = id;
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
