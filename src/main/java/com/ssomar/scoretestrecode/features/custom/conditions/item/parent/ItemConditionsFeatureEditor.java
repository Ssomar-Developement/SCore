package com.ssomar.scoretestrecode.features.custom.conditions.item.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class ItemConditionsFeatureEditor extends FeatureEditorInterface<ItemConditionsFeature> {

    public ItemConditionsFeature bCF;

    public ItemConditionsFeatureEditor(ItemConditionsFeature dropFeatures) {
        super("&lItem Conditions Editor", 3 * 9);
        this.bCF = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (ItemConditionFeature condition : bCF.getConditions()) {
            condition.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ItemConditionsFeature getParent() {
        return bCF;
    }
}
