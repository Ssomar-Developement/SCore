package com.ssomar.score.features.custom.required.executableitems.group;

import com.ssomar.score.features.custom.required.executableitems.item.RequiredExecutableItemFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RequiredExecutableItemGroupFeatureEditor extends FeatureEditorInterface<RequiredExecutableItemGroupFeature> {

    public final RequiredExecutableItemGroupFeature attributesGroupFeature;

    public RequiredExecutableItemGroupFeatureEditor(RequiredExecutableItemGroupFeature enchantsGroupFeature) {
        super("&lRequired ExecutableItems feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (RequiredExecutableItemFeature enchantment : attributesGroupFeature.getRequiredExecutableItems().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }
        attributesGroupFeature.getErrorMessage().initAndUpdateItemParentEditor(this, i);
        i++;
        attributesGroupFeature.getCancelEventIfError().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new required ExecutableItem");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredExecutableItemGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
