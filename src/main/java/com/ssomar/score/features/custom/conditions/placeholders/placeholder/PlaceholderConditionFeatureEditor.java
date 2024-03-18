package com.ssomar.score.features.custom.conditions.placeholders.placeholder;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class PlaceholderConditionFeatureEditor extends FeatureEditorInterface<PlaceholderConditionFeature> {

    public final PlaceholderConditionFeature enchantFeature;

    public PlaceholderConditionFeatureEditor(PlaceholderConditionFeature dropFeatures) {
        super("&lPlaceholder condition feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getType().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getPart1().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getComparator().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getPart2().initAndUpdateItemParentEditor(this, 3);
        enchantFeature.getMessageIfNotValid().initAndUpdateItemParentEditor(this, 4);
        enchantFeature.getMessageIfNotValidForTarget().initAndUpdateItemParentEditor(this, 5);
        enchantFeature.getCancelEventIfNotValid().initAndUpdateItemParentEditor(this, 6);
        enchantFeature.getConsoleCommandsIfError().initAndUpdateItemParentEditor(this, 7);
        enchantFeature.getStopCheckingOtherConditionsIfNotValid().initAndUpdateItemParentEditor(this, 8);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PlaceholderConditionFeature getParent() {
        return enchantFeature;
    }
}
