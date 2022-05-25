package com.ssomar.testRecode.features.custom.givefirstjoin;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class GiveFirstJoinFeaturesEditor extends FeatureEditorInterface<GiveFirstJoinFeatures> {

    public GiveFirstJoinFeatures dropFeatures;

    public GiveFirstJoinFeaturesEditor(GiveFirstJoinFeatures dropFeatures) {
        super("&lGive first join features Editor", 3*9);
        this.dropFeatures = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        dropFeatures.getGiveFirstJoin().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getGiveFirstJoinAmount().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getGiveFirstJoinSlot().initAndUpdateItemParentEditor(this, 2);


        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public GiveFirstJoinFeatures getParent() {
        return dropFeatures;
    }
}
