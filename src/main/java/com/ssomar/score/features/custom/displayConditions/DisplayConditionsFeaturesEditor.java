package com.ssomar.score.features.custom.displayConditions;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DisplayConditionsFeaturesEditor extends FeatureEditorInterface<DisplayConditionsFeatures> {

    public final DisplayConditionsFeatures dropFeatures;

    public DisplayConditionsFeaturesEditor(DisplayConditionsFeatures dropFeatures) {
        super("&lDisplay conditions Features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        dropFeatures.getEnableFeature().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getPlayerConditions().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getWorldConditions().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getItemConditions().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getPlaceholderConditions().initAndUpdateItemParentEditor(this, i);
        i++;

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DisplayConditionsFeatures getParent() {
        return dropFeatures;
    }
}
