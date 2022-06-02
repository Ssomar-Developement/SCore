package com.ssomar.testRecode.features.custom.attributes.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.custom.attributes.attribute.AttributeFullOptionsFeature;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class AttributesGroupFeatureEditor extends FeatureEditorInterface<AttributesGroupFeature> {

    public AttributesGroupFeature attributesGroupFeature;

    public AttributesGroupFeatureEditor(AttributesGroupFeature enchantsGroupFeature) {
        super("&lAttributes feature Editor", 3*9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(AttributeFullOptionsFeature enchantment : attributesGroupFeature.getAttributes().values()) {
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
    public AttributesGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
