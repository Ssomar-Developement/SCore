package com.ssomar.score.features.custom.required.parent;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RequiredGroupEditor extends FeatureEditorInterface<RequiredGroup> {

    public final RequiredGroup requiredGroup;

    public RequiredGroupEditor(RequiredGroup requiredLevel) {
        super("&lRequired Things Editor", 3 * 9);
        this.requiredGroup = requiredLevel.clone(requiredLevel.getParent());
        load();
    }

    @Override
    public void load() {
        requiredGroup.getRequiredLevel().initAndUpdateItemParentEditor(this, 0);
        requiredGroup.getRequiredExperience().initAndUpdateItemParentEditor(this, 1);
        requiredGroup.getRequiredMoney().initAndUpdateItemParentEditor(this, 2);
        requiredGroup.getRequiredItems().initAndUpdateItemParentEditor(this, 3);
        requiredGroup.getRequiredExecutableItems().initAndUpdateItemParentEditor(this, 4);
        requiredGroup.getRequiredMana().initAndUpdateItemParentEditor(this, 5);
        requiredGroup.getRequiredMagics().initAndUpdateItemParentEditor(this, 6);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredGroup getParent() {
        return requiredGroup;
    }
}
