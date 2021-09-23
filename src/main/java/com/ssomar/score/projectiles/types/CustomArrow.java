package com.ssomar.score.projectiles.types;


import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;

public class CustomArrow extends CustomProjectile {

    CustomProjectile customArrow;
    String id;

    public CustomArrow(String id) {
        configGui = new SimpleGUI("EDITORE CURSTOM PROJECTILES ARROW", 5*9);
        this.id = id;
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIdentifierType() {
        return identifierType;
    }
}
