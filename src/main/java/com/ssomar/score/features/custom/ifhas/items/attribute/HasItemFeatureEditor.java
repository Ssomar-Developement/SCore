package com.ssomar.score.features.custom.ifhas.items.attribute;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class HasItemFeatureEditor extends FeatureEditorInterface<HasItemFeature> {

    public HasItemFeature enchantFeature;

    public HasItemFeatureEditor(HasItemFeature dropFeatures) {
        super("&lHas Item feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getMaterial().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getAmount().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getDetailedSlots().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public HasItemFeature getParent() {
        return enchantFeature;
    }
}
