package com.ssomar.scoretestrecode.features.custom.variables.update.variable;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.VariableType;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class VariableUpdateFeatureEditor extends FeatureEditorInterface<VariableUpdateFeature> {

    public VariableUpdateFeature enchantFeature;

    public VariableUpdateFeatureEditor(VariableUpdateFeature dropFeatures) {
        super("&lVariable Update feature Editor", 3*9);
        this.enchantFeature = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        enchantFeature.getVariableName().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getType().initAndUpdateItemParentEditor(this, 1);
        if(enchantFeature.getVariables().getVariablesName().contains(enchantFeature.getVariableName().getValue().get())) {
            VariableType variableType = enchantFeature.getVariables().getVariable(enchantFeature.getVariableName().getValue().get()).getType().getValue().get();
            if(variableType.equals(VariableType.STRING)){
                enchantFeature.getStringUpdate().initAndUpdateItemParentEditor(this, 2);
            }
            else if(variableType.equals(VariableType.NUMBER)){
                enchantFeature.getDoubleUpdate().initAndUpdateItemParentEditor(this, 2);
            }
        }
        else enchantFeature.getStringUpdate().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public VariableUpdateFeature getParent() {
        return enchantFeature;
    }
}
