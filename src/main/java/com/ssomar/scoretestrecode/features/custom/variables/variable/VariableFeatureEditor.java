package com.ssomar.scoretestrecode.features.custom.variables.variable;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.VariableType;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class VariableFeatureEditor extends FeatureEditorInterface<VariableFeature> {

    public VariableFeature enchantFeature;

    public VariableFeatureEditor(VariableFeature dropFeatures) {
        super("&lVariable feature Editor", 3*9);
        this.enchantFeature = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        enchantFeature.getVariableName().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getType().initAndUpdateItemParentEditor(this, 1);
        if(enchantFeature.getType().getValue().get().equals(VariableType.STRING)){
            enchantFeature.getStringValue().initAndUpdateItemParentEditor(this, 2);
        }
        else if(enchantFeature.getType().getValue().get().equals(VariableType.NUMBER)){
            enchantFeature.getDoubleValue().initAndUpdateItemParentEditor(this, 2);
        }

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public VariableFeature getParent() {
        return enchantFeature;
    }
}
