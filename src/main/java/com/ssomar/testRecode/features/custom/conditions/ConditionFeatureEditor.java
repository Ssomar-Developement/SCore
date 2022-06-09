package com.ssomar.testRecode.features.custom.conditions;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.custom.hiders.Hiders;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class ConditionFeatureEditor extends FeatureEditorInterface<ConditionFeature> {

    public ConditionFeature condition;

    public ConditionFeatureEditor(ConditionFeature condition) {
        super("&l"+condition.getEditorName()+" Editor", 3*9);
        this.condition = condition.clone();
        load();
    }

    @Override
    public void load() {
        condition.getCondition().initItemParentEditor(this, 0);
        condition.getErrorMessage().initItemParentEditor(this, 1);
        condition.getCancelEventIfError().initItemParentEditor(this, 2);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ConditionFeature getParent() {
        return condition;
    }
}
