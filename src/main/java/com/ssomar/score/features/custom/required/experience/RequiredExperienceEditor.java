package com.ssomar.score.features.custom.required.experience;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RequiredExperienceEditor extends FeatureEditorInterface<RequiredExperience> {

    public final RequiredExperience requiredExperience;

    public RequiredExperienceEditor(RequiredExperience requiredLevel) {
        super("&lRequired Experience Editor", 3 * 9);
        this.requiredExperience = requiredLevel.clone(requiredLevel.getParent());
        load();
    }

    @Override
    public void load() {
        requiredExperience.getExperience().initAndUpdateItemParentEditor(this, 0);
        requiredExperience.getCancelEventIfError().initAndUpdateItemParentEditor(this, 1);
        requiredExperience.getErrorMessage().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredExperience getParent() {
        return requiredExperience;
    }
}
