package com.ssomar.score.features.custom.conditions.placeholders.group;

import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class PlaceholderConditionGroupFeatureEditor extends FeatureEditorInterface<PlaceholderConditionGroupFeature> {

    public PlaceholderConditionGroupFeature attributesGroupFeature;

    public PlaceholderConditionGroupFeatureEditor(PlaceholderConditionGroupFeature enchantsGroupFeature) {
        super("&lPlaceholders Conditions feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (PlaceholderConditionFeature enchantment : attributesGroupFeature.getAttributes().values()) {
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
    public PlaceholderConditionGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
