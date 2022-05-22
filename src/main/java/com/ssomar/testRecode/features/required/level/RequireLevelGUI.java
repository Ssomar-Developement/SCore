package com.ssomar.testRecode.features.required.level;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class RequireLevelGUI extends FeatureEditorInterface<RequiredLevel> {

    public RequiredLevel requiredLevel;

    public RequireLevelGUI(RequiredLevel requiredLevel) {
        super("Required Level Editor", 3*9);
        this.requiredLevel = requiredLevel.clone();
    }

    @Override
    public void load() {
        requiredLevel.getLevel().initItemParentEditor(this, 0).updateItemParentEditor(this);
        requiredLevel.getCancelEventIfError().initItemParentEditor(this, 1).updateItemParentEditor(this);
        requiredLevel.getErrorMessage().initItemParentEditor(this, 2).updateItemParentEditor(this);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredLevel getParent() {
        return requiredLevel;
    }
}
