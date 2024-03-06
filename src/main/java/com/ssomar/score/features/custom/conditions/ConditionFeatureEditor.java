package com.ssomar.score.features.custom.conditions;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ConditionFeatureEditor extends FeatureEditorInterface<ConditionFeature> {

    public final ConditionFeature condition;

    public ConditionFeatureEditor(ConditionFeature condition) {
        super("&l" + condition.getEditorName() + " Editor", 3 * 9);
        this.condition = condition.clone(condition.getParent());
        load();
    }

    @Override
    public void load() {
        condition.getCondition().initAndUpdateItemParentEditor(this, 0);
        condition.getErrorMessage().initAndUpdateItemParentEditor(this, 1);
        condition.getCancelEventIfError().initAndUpdateItemParentEditor(this, 2);
        condition.getConsoleCommandsIfError().initAndUpdateItemParentEditor(this, 3);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ConditionFeature getParent() {
        return condition;
    }
}
