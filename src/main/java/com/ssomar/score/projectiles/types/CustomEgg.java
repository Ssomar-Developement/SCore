package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomEgg extends CustomProjectile {

        CustomProjectile customEgg;
        String id;

    public CustomEgg(String id) {
        super(id);
    }

    public CustomEgg(String id, FileConfiguration projConfig) {
        super(id, projConfig);
    }

    @Override
    public void setup() {
        customEgg = new CustomNameFeature(this);
        customEgg = new InvisibleFeature(customEgg);
        customEgg = new GlowingFeature(customEgg);
        customEgg = new BounceFeature(customEgg);
        customEgg = new GravityFeature(customEgg);
        customEgg = new DespawnFeature(customEgg);
        customEgg = new VelocityFeature(customEgg);
        customEgg = new SilentFeature(customEgg);
        customEgg = new ParticlesFeature(customEgg);
        customEgg = new VisualItemFeature(customEgg);
    }

    @Override
        public CustomProjectile getLoaded() {
            return customEgg;
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
