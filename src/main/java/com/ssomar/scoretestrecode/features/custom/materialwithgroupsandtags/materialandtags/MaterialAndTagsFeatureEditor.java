package com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.materialandtags;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class MaterialAndTagsFeatureEditor extends FeatureEditorInterface<MaterialAndTagsFeature> {

    public MaterialAndTagsFeature enchantFeature;

    public MaterialAndTagsFeatureEditor(MaterialAndTagsFeature dropFeatures) {
        super("&lMaterial and tags feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getMaterial().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getTags().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public MaterialAndTagsFeature getParent() {
        return enchantFeature;
    }
}
