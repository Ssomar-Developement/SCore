package com.ssomar.score.features.custom.detailedeffects;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DetailedEffectsEditor extends FeatureEditorInterface<DetailedEffects> {

    public final DetailedEffects dropFeatures;

    public DetailedEffectsEditor(DetailedEffects dropFeatures) {
        super("&lDetailed Effects Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getEffects().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getMessageIfNotValid().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getCancelEventIfNotValid().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DetailedEffects getParent() {
        return dropFeatures;
    }
}
