package com.ssomar.scoretestrecode.features.custom.headfeatures;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class HeadFeaturesEditor extends FeatureEditorInterface<HeadFeatures> {

    public HeadFeatures dropFeatures;

    public HeadFeaturesEditor(HeadFeatures dropFeatures) {
        super("&lHead features Editor", 3*9);
        this.dropFeatures = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        dropFeatures.getHeadValue().initItemParentEditor(this, 0);
        dropFeatures.getHeadDBID().initItemParentEditor(this, 1);


        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public HeadFeatures getParent() {
        return dropFeatures;
    }
}
