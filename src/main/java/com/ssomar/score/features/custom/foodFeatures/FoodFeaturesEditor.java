package com.ssomar.score.features.custom.foodFeatures;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class FoodFeaturesEditor extends FeatureEditorInterface<FoodFeatures> {

    public final FoodFeatures dropFeatures;

    public FoodFeaturesEditor(FoodFeatures dropFeatures) {
        super("&lFood Features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        dropFeatures.getIsMeat().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getNutrition().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getSaturation().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getCanAlwaysEat().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getEatSeconds().initAndUpdateItemParentEditor(this, i);
        i++;

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public FoodFeatures getParent() {
        return dropFeatures;
    }
}
