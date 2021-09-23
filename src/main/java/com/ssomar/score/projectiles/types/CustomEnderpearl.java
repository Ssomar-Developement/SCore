package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;

public class CustomEnderpearl extends CustomProjectile {

    CustomProjectile customEnderpearl;
    String id;

    public CustomEnderpearl(String id) {
        configGui = new SimpleGUI("EDITORE CURSTOM PROJECTILES ARROW", 5*9);
        this.id = id;
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
    public String getId() {
        return id;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
