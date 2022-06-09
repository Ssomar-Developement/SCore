package com.ssomar.testRecode.features.custom.required.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class RequiredGroupEditor extends FeatureEditorInterface<RequiredGroup> {

    public RequiredGroup requiredGroup;

    public RequiredGroupEditor(RequiredGroup requiredLevel) {
        super("&lRequired Things Editor", 3*9);
        this.requiredGroup = requiredLevel.clone();
        load();
    }

    @Override
    public void load() {
        requiredGroup.getRequiredLevel().initAndUpdateItemParentEditor(this, 0);
        requiredGroup.getRequiredMoney().initAndUpdateItemParentEditor(this, 1);
        requiredGroup.getRequiredItems().initAndUpdateItemParentEditor(this, 2);
        requiredGroup.getRequiredExecutableItems().initAndUpdateItemParentEditor(this, 3);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredGroup getParent() {
        return requiredGroup;
    }
}
