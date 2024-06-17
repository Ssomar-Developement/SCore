package com.ssomar.score.features.custom.brewingstand;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class BrewingStandFeaturesEditor extends FeatureEditorInterface<BrewingStandFeatures> {

    public BrewingStandFeatures brewingStandFeatures;

    public BrewingStandFeaturesEditor(BrewingStandFeatures containerFeatures) {
        super("&lBrewing Stand features Editor", 3 * 9);
        this.brewingStandFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        brewingStandFeatures.getBrewingStandSpeed().initAndUpdateItemParentEditor(this, i);



        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public BrewingStandFeatures getParent() {
        return brewingStandFeatures;
    }
}
