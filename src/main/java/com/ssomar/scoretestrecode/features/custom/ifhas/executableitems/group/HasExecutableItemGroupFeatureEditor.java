package com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.attribute.HasExecutableItemFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class HasExecutableItemGroupFeatureEditor extends FeatureEditorInterface<HasExecutableItemGroupFeature> {

    public HasExecutableItemGroupFeature attributesGroupFeature;

    public HasExecutableItemGroupFeatureEditor(HasExecutableItemGroupFeature enchantsGroupFeature) {
        super("&lHas ExecutableItems feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (HasExecutableItemFeature enchantment : attributesGroupFeature.getHasExecutableItems().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new attribute");
    }

    @Override
    public HasExecutableItemGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
