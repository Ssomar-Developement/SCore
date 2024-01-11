package com.ssomar.score.features.custom.conditions.entity.parent;

import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class EntityConditionsFeatureEditor extends FeatureEditorInterface<EntityConditionsFeature> {

    public final EntityConditionsFeature bCF;

    public EntityConditionsFeatureEditor(EntityConditionsFeature bCF) {
        super("&lEntity Conditions Editor", 4 * 9);
        this.bCF = bCF.clone(bCF.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (EntityConditionFeature condition : bCF.getConditions()) {
            condition.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 27, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 28, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 35, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public EntityConditionsFeature getParent() {
        return bCF;
    }
}
