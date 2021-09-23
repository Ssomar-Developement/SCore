package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.*;

public class CustomEgg extends CustomProjectile {

        CustomProjectile customEgg;
        String id;

        public CustomEgg(String id) {
            configGui = new SimpleGUI("EDITORE CURSTOM PROJECTILES ARROW", 5*9);
            this.id = id;
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
