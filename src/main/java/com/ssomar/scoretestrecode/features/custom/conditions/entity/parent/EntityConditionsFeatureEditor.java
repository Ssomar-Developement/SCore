package com.ssomar.scoretestrecode.features.custom.conditions.entity.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class EntityConditionsFeatureEditor extends FeatureEditorInterface<EntityConditionsFeature> {

    public EntityConditionsFeature bCF;

    public EntityConditionsFeatureEditor(EntityConditionsFeature bCF) {
        super("&lEntity Conditions Editor", 3 * 9);
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
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public EntityConditionsFeature getParent() {
        return bCF;
    }
}
