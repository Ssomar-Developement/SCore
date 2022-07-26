package com.ssomar.score.features.custom.conditions.world.parent;

import com.ssomar.score.features.custom.conditions.world.WorldConditionFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class WorldConditionsFeatureEditor extends FeatureEditorInterface<WorldConditionsFeature> {

    public WorldConditionsFeature bCF;

    public WorldConditionsFeatureEditor(WorldConditionsFeature dropFeatures) {
        super("&lWorld Conditions Editor", 3 * 9);
        this.bCF = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (WorldConditionFeature condition : bCF.getConditions()) {
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
    public WorldConditionsFeature getParent() {
        return bCF;
    }
}
