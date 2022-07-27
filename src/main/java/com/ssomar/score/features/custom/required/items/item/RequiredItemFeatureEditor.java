package com.ssomar.score.features.custom.required.items.item;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RequiredItemFeatureEditor extends FeatureEditorInterface<RequiredItemFeature> {

    public final RequiredItemFeature enchantFeature;

    public RequiredItemFeatureEditor(RequiredItemFeature dropFeatures) {
        super("&lRequired Item feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getMaterial().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getAmount().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getNotExecutableItem().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredItemFeature getParent() {
        return enchantFeature;
    }
}
