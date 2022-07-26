package com.ssomar.score.features.custom.potioneffects.potioneffect;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class PotionEffectFeatureEditor extends FeatureEditorInterface<PotionEffectFeature> {

    public PotionEffectFeature enchantFeature;

    public PotionEffectFeatureEditor(PotionEffectFeature dropFeatures) {
        super("&lPotion Effect feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getType().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getAmplifier().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getDuration().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getAmbient().initAndUpdateItemParentEditor(this, 3);
        enchantFeature.getParticles().initAndUpdateItemParentEditor(this, 4);
        if (!SCore.is1v12Less()) enchantFeature.getIcon().initAndUpdateItemParentEditor(this, 5);


        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PotionEffectFeature getParent() {
        return enchantFeature;
    }
}
