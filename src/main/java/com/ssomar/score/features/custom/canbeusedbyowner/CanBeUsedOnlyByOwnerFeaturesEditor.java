package com.ssomar.score.features.custom.canbeusedbyowner;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class CanBeUsedOnlyByOwnerFeaturesEditor extends FeatureEditorInterface<CanBeUsedOnlyByOwnerFeatures> {

    public final CanBeUsedOnlyByOwnerFeatures dropFeatures;

    public CanBeUsedOnlyByOwnerFeaturesEditor(CanBeUsedOnlyByOwnerFeatures dropFeatures) {
        super("&lCan Be Used Only By The Owner Features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        dropFeatures.getCanBeUsedOnlyByTheOwner().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getCancelEventIfNotOwner().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getBlackListedActivators().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public CanBeUsedOnlyByOwnerFeatures getParent() {
        return dropFeatures;
    }
}
