package com.ssomar.score.features.custom.patterns.subpattern;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class SubPatternFeatureEditor extends FeatureEditorInterface<SubPatternFeature> {

    public SubPatternFeature enchantFeature;

    public SubPatternFeatureEditor(SubPatternFeature dropFeatures) {
        super("&lPattern feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getString().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getObject().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public SubPatternFeature getParent() {
        return enchantFeature;
    }
}
