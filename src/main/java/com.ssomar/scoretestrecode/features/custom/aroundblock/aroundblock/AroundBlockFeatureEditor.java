package com.ssomar.scoretestrecode.features.custom.aroundblock.aroundblock;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class AroundBlockFeatureEditor extends FeatureEditorInterface<AroundBlockFeature> {

    public AroundBlockFeature enchantFeature;

    public AroundBlockFeatureEditor(AroundBlockFeature dropFeatures) {
        super("&lAroundBlock feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getSouthValue().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getNorthValue().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getWestValue().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getEastValue().initAndUpdateItemParentEditor(this, 3);
        enchantFeature.getAboveValue().initAndUpdateItemParentEditor(this, 4);
        enchantFeature.getUnderValue().initAndUpdateItemParentEditor(this, 5);

        enchantFeature.getErrorMessage().initAndUpdateItemParentEditor(this, 6);

        enchantFeature.getBlockMustBeExecutableBlock().initAndUpdateItemParentEditor(this, 7);

        enchantFeature.getBlockTypeMustBe().initAndUpdateItemParentEditor(this, 8);

        enchantFeature.getBlockTypeMustNotBe().initAndUpdateItemParentEditor(this, 9);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public AroundBlockFeature getParent() {
        return enchantFeature;
    }
}
