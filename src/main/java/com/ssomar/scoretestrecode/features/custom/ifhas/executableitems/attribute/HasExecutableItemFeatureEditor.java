package com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.attribute;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class HasExecutableItemFeatureEditor extends FeatureEditorInterface<HasExecutableItemFeature> {

    public HasExecutableItemFeature enchantFeature;

    public HasExecutableItemFeatureEditor(HasExecutableItemFeature dropFeatures) {
        super("&lHas ExecutableItem feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getExecutableItem().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getAmount().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getDetailedSlots().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getUsageCondition().initAndUpdateItemParentEditor(this, 3);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public HasExecutableItemFeature getParent() {
        return enchantFeature;
    }
}
