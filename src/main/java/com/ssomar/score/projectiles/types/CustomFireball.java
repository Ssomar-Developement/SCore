package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;

public class CustomFireball extends CustomProjectile {

    CustomProjectile customFireball;
    String id;

    public CustomFireball(String id) {
        configGui = new SimpleGUI("EDITORE CURSTOM PROJECTILES ARROW", 5*9);
        this.id = id;
        customFireball = new CustomNameFeature(this);
        customFireball = new InvisibleFeature(customFireball);
        customFireball = new GlowingFeature(customFireball);
        customFireball = new BounceFeature(customFireball);
        customFireball = new GravityFeature(customFireball);
        customFireball = new DespawnFeature(customFireball);
        customFireball = new VelocityFeature(customFireball);
        customFireball = new SilentFeature(customFireball);
        customFireball = new ParticlesFeature(customFireball);
        customFireball = new RadiusFeature(customFireball);
        customFireball = new IncendiaryFeature(customFireball);

    }

    @Override
    public CustomProjectile getLoaded() {
        return customFireball;
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
