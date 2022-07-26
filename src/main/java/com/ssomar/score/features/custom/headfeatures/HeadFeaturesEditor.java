package com.ssomar.score.features.custom.headfeatures;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class HeadFeaturesEditor extends FeatureEditorInterface<HeadFeatures> {

    public HeadFeatures dropFeatures;

    public HeadFeaturesEditor(HeadFeatures dropFeatures) {
        super("&lHead features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        if (dropFeatures.getFeatures().contains(dropFeatures.getHeadValue())) {
            dropFeatures.getHeadValue().initAndUpdateItemParentEditor(this, i);
            i++;
        }
        dropFeatures.getHeadDBID().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public HeadFeatures getParent() {
        return dropFeatures;
    }
}
