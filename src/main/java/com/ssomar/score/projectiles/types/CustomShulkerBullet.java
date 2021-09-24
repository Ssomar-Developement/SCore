package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomShulkerBullet extends CustomProjectile {

    CustomProjectile customShulkerBullet;
    String id;

    public CustomShulkerBullet(String id) {
        super(id);
      }

    public CustomShulkerBullet(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customShulkerBullet = new CustomNameFeature(this);
        customShulkerBullet = new InvisibleFeature(customShulkerBullet);
        customShulkerBullet = new GlowingFeature(customShulkerBullet);
        customShulkerBullet = new BounceFeature(customShulkerBullet);
        customShulkerBullet = new GravityFeature(customShulkerBullet);
        customShulkerBullet = new DespawnFeature(customShulkerBullet);
        customShulkerBullet = new VelocityFeature(customShulkerBullet);
        customShulkerBullet = new SilentFeature(customShulkerBullet);
        customShulkerBullet = new ParticlesFeature(customShulkerBullet);
    }

    @Override
    public CustomProjectile getLoaded() {
        return customShulkerBullet;
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
