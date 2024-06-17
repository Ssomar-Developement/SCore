package com.ssomar.score.features.custom.hopper;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class HopperFeaturesEditor extends FeatureEditorInterface<HopperFeatures> {

    public HopperFeatures containerFeatures;

    public HopperFeaturesEditor(HopperFeatures containerFeatures) {
        super("&lHopper features Editor", 3 * 9);
        this.containerFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        containerFeatures.getAmountItemsTransferred().initAndUpdateItemParentEditor(this, i);



        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public HopperFeatures getParent() {
        return containerFeatures;
    }
}
