package com.ssomar.score.features.custom.aroundblock.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.custom.aroundblock.aroundblock.AroundBlockFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class AroundBlockGroupFeatureEditor extends FeatureEditorInterface<AroundBlockGroupFeature> {

    public AroundBlockGroupFeature attributesGroupFeature;

    public AroundBlockGroupFeatureEditor(AroundBlockGroupFeature enchantsGroupFeature) {
        super("&lAroundBlockGroup feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (AroundBlockFeature enchantment : attributesGroupFeature.getAroundBlockGroup().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new attribute
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new around block cdt");
    }

    @Override
    public AroundBlockGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
