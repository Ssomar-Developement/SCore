package com.ssomar.scoretestrecode.features.custom.required.items.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.required.items.item.RequiredItemFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class RequiredItemGroupFeatureEditor extends FeatureEditorInterface<RequiredItemGroupFeature> {

    public RequiredItemGroupFeature attributesGroupFeature;

    public RequiredItemGroupFeatureEditor(RequiredItemGroupFeature enchantsGroupFeature) {
        super("&lRequired Items feature Editor", 3*9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(RequiredItemFeature enchantment : attributesGroupFeature.getRequiredItems().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }
        attributesGroupFeature.getErrorMessage().initAndUpdateItemParentEditor(this, i);
        i++;
        attributesGroupFeature.getCancelEventIfError().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new required item");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredItemGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
