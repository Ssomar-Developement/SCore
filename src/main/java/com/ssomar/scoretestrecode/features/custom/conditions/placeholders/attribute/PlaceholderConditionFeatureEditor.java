package com.ssomar.scoretestrecode.features.custom.conditions.placeholders.attribute;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class PlaceholderConditionFeatureEditor extends FeatureEditorInterface<PlaceholderCondition> {

    public PlaceholderCondition enchantFeature;

    public PlaceholderConditionFeatureEditor(PlaceholderCondition dropFeatures) {
        super("&lPlaceholder condition feature Editor", 3*9);
        this.enchantFeature = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        enchantFeature.getType().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getPart1().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getComparator().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getPart2().initAndUpdateItemParentEditor(this, 3);
        enchantFeature.getMessageIfNotValid().initAndUpdateItemParentEditor(this, 4);
        enchantFeature.getCancelEventIfNotValid().initAndUpdateItemParentEditor(this, 5);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PlaceholderCondition getParent() {
        return enchantFeature;
    }
}
