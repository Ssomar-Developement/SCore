package com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.materialandtags.MaterialAndTagsFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class MaterialAndTagsGroupFeatureEditor extends FeatureEditorInterface<MaterialAndTagsGroupFeature> {

    public MaterialAndTagsGroupFeature attributesGroupFeature;

    public MaterialAndTagsGroupFeatureEditor(MaterialAndTagsGroupFeature enchantsGroupFeature) {
        super("&lMaterials feature Editor", 3*9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(MaterialAndTagsFeature enchantment : attributesGroupFeature.getMaterialAndTags().values()) {
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
    public MaterialAndTagsGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
