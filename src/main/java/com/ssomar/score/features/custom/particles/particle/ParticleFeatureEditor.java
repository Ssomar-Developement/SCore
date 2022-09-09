package com.ssomar.score.features.custom.particles.particle;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ParticleFeatureEditor extends FeatureEditorInterface<ParticleFeature> {

    public final ParticleFeature sparticleFeature;

    public ParticleFeatureEditor(ParticleFeature dropFeatures) {
        super("&lParticle feature Editor", 3 * 9);
        this.sparticleFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        sparticleFeature.getParticlesType().initAndUpdateItemParentEditor(this, 0);
        sparticleFeature.getParticlesAmount().initAndUpdateItemParentEditor(this, 1);
        sparticleFeature.getParticlesSpeed().initAndUpdateItemParentEditor(this, 2);
        sparticleFeature.getParticlesOffSet().initAndUpdateItemParentEditor(this, 3);
        sparticleFeature.getParticlesDelay().initAndUpdateItemParentEditor(this, 4);
        if(sparticleFeature.canHaveRedstoneColor()) sparticleFeature.getRedstoneColor().initAndUpdateItemParentEditor(this, 5);
        else if(sparticleFeature.canHaveBlocktype()) sparticleFeature.getBlockType().initAndUpdateItemParentEditor(this, 5);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ParticleFeature getParent() {
        return sparticleFeature;
    }
}
