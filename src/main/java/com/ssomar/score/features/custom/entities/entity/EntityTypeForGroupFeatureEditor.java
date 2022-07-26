package com.ssomar.score.features.custom.entities.entity;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class EntityTypeForGroupFeatureEditor extends FeatureEditorInterface<EntityTypeForGroupFeature> {

    public EntityTypeForGroupFeature enchantFeature;

    public EntityTypeForGroupFeatureEditor(EntityTypeForGroupFeature dropFeatures) {
        super("&lEntityType feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getEntityType().initAndUpdateItemParentEditor(this, 0);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public EntityTypeForGroupFeature getParent() {
        return enchantFeature;
    }
}
