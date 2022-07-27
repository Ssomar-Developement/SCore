package com.ssomar.score.features.custom.variables.update.group;

import com.ssomar.score.features.custom.variables.update.variable.VariableUpdateFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class VariableUpdateGroupFeatureEditor extends FeatureEditorInterface<VariableUpdateGroupFeature> {

    public final VariableUpdateGroupFeature attributesGroupFeature;

    public VariableUpdateGroupFeatureEditor(VariableUpdateGroupFeature enchantsGroupFeature) {
        super("&lVariables updates feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (VariableUpdateFeature enchantment : attributesGroupFeature.getVariablesUpdates().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new attribute");
    }

    @Override
    public VariableUpdateGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
