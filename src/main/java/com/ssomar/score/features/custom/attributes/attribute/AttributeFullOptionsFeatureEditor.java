package com.ssomar.score.features.custom.attributes.attribute;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class AttributeFullOptionsFeatureEditor extends FeatureEditorInterface<AttributeFullOptionsFeature> {

    public final AttributeFullOptionsFeature enchantFeature;

    public AttributeFullOptionsFeatureEditor(AttributeFullOptionsFeature dropFeatures) {
        super("&lAttribute feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getAttribute().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getOperation().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getAmount().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getSlot().initAndUpdateItemParentEditor(this, 3);
        enchantFeature.getAttributeName().initAndUpdateItemParentEditor(this, 4);
        enchantFeature.getUuid().initAndUpdateItemParentEditor(this, 5);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public AttributeFullOptionsFeature getParent() {
        return enchantFeature;
    }
}
