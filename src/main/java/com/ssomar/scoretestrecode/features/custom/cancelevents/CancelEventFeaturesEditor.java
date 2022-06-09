package com.ssomar.scoretestrecode.features.custom.cancelevents;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class CancelEventFeaturesEditor extends FeatureEditorInterface<CancelEventFeatures> {

    public CancelEventFeatures dropFeatures;

    public CancelEventFeaturesEditor(CancelEventFeatures dropFeatures) {
        super("&lCancelEvent features Editor", 3*9);
        this.dropFeatures = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        dropFeatures.getCancelEventIfNoperm().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getCancelEventIfNotOwner().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public CancelEventFeatures getParent() {
        return dropFeatures;
    }
}
