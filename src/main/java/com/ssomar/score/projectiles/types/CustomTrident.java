package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomTrident extends CustomProjectile {

   CustomProjectile customTrident;
    String id;

    public CustomTrident(String id) {
     super(id);
    }

    public CustomTrident(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customTrident = new CustomNameFeature(this);
        customTrident = new InvisibleFeature(customTrident);
        customTrident = new PickupFeature(customTrident);
        customTrident = new GlowingFeature(customTrident);
        customTrident = new CriticalFeature(customTrident);
        customTrident = new BounceFeature(customTrident);
        customTrident = new GravityFeature(customTrident);
        customTrident = new DamageFeature(customTrident);
        customTrident = new KnockbackStrengthFeature(customTrident);
        customTrident = new PierceLevelFeature(customTrident);
        customTrident = new DespawnFeature(customTrident);
        customTrident = new VelocityFeature(customTrident);
        customTrident = new SilentFeature(customTrident);
        customTrident = new ParticlesFeature(customTrident);
        customTrident = new VisualItemFeature(customTrident);
        customTrident = new EnchantmentsFeature(customTrident);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customTrident;
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
