package com.ssomar.scoretestrecode.features.custom.required.executableitems.item;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class RequiredExecutableItemFeatureEditor extends FeatureEditorInterface<RequiredExecutableItemFeature> {

    public RequiredExecutableItemFeature requiredExecutableItemFeature;

    public RequiredExecutableItemFeatureEditor(RequiredExecutableItemFeature dropFeatures) {
        super("&lRequired ExecutableItem feature Editor", 3 * 9);
        this.requiredExecutableItemFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        requiredExecutableItemFeature.getExecutableItem().initAndUpdateItemParentEditor(this, 0);
        requiredExecutableItemFeature.getAmount().initAndUpdateItemParentEditor(this, 1);
        requiredExecutableItemFeature.getUsageCondition().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredExecutableItemFeature getParent() {
        return requiredExecutableItemFeature;
    }
}
