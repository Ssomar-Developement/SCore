package com.ssomar.testRecode.features.custom.required.items.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.custom.required.items.item.RequiredItemFeature;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class RequiredItemGroupFeatureEditor extends FeatureEditorInterface<RequiredItemGroupFeature> {

    public RequiredItemGroupFeature attributesGroupFeature;

    public RequiredItemGroupFeatureEditor(RequiredItemGroupFeature enchantsGroupFeature) {
        super("&lAttributes feature Editor", 3*9);
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

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new attribute");
    }

    @Override
    public RequiredItemGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
