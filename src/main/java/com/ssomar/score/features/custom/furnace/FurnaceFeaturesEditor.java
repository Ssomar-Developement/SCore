package com.ssomar.score.features.custom.furnace;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class FurnaceFeaturesEditor extends FeatureEditorInterface<FurnaceFeatures> {

    public FurnaceFeatures furnaceFeatures;

    public FurnaceFeaturesEditor(FurnaceFeatures containerFeatures) {
        super("&lFurnace features Editor", 3 * 9);
        this.furnaceFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        furnaceFeatures.getFurnaceSpeed().initAndUpdateItemParentEditor(this, i);
        i++;
        furnaceFeatures.getInfiniteFuel().initAndUpdateItemParentEditor(this, i);
        i++;
        furnaceFeatures.getInfiniteVisualLit().initAndUpdateItemParentEditor(this, i);
        i++;
        furnaceFeatures.getFortuneChance().initAndUpdateItemParentEditor(this, i);
        i++;
        furnaceFeatures.getFortuneMultiplier().initAndUpdateItemParentEditor(this, i);



        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public FurnaceFeatures getParent() {
        return furnaceFeatures;
    }
}
