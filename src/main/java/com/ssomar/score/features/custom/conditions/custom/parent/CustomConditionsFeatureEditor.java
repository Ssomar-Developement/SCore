package com.ssomar.score.features.custom.conditions.custom.parent;

import com.ssomar.score.features.custom.conditions.custom.CustomConditionFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class CustomConditionsFeatureEditor extends FeatureEditorInterface<CustomConditionsFeature> {

    public final CustomConditionsFeature bCF;

    public CustomConditionsFeatureEditor(CustomConditionsFeature dropFeatures) {
        super("&lCustom EI Conditions Editor", 3 * 9);
        this.bCF = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (CustomConditionFeature condition : bCF.getConditions()) {
            condition.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public CustomConditionsFeature getParent() {
        return bCF;
    }
}
