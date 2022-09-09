package com.ssomar.score.features.custom.particles.group;

import com.ssomar.score.features.custom.particles.particle.ParticleFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ParticlesGroupFeatureEditor extends FeatureEditorInterface<ParticlesGroupFeature> {

    public final ParticlesGroupFeature enchantsGroupFeature;

    public ParticlesGroupFeatureEditor(ParticlesGroupFeature enchantsGroupFeature) {
        super("&lParticles feature Editor", 3 * 9);
        this.enchantsGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (ParticleFeature enchantment : enchantsGroupFeature.getParticles().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new particle");
    }

    @Override
    public ParticlesGroupFeature getParent() {
        return enchantsGroupFeature;
    }
}
