package com.ssomar.score.features.custom.aroundblock.aroundblock;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class AroundBlockFeatureEditor extends FeatureEditorInterface<AroundBlockFeature> {

    public final AroundBlockFeature enchantFeature;

    public AroundBlockFeatureEditor(AroundBlockFeature dropFeatures) {
        super(TM.g(Text.FEATURES_AROUNDBLOCK_EDITORTITLE), 3 * 9);
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

        enchantFeature.getBlockTypeMustBe().initAndUpdateItemParentEditor(this, 7);
        enchantFeature.getPlaceholderConditions().initAndUpdateItemParentEditor(this, 8);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, TM.g(Text.EDITOR_RESET_NAME), false, false, "", TM.g(Text.EDITOR_RESET_DESCRIPTION));

        // Save menu
        createItem(GREEN, 1, 26, TM.g(Text.EDITOR_SAVE_NAME), false, false, "", TM.g(Text.EDITOR_SAVE_DESCRIPTION));
    }

    @Override
    public AroundBlockFeature getParent() {
        return enchantFeature;
    }
}
