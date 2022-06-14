package com.ssomar.scoretestrecode.features.custom.conditions.player.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class PlayerConditionsFeatureEditor extends FeatureEditorInterface<PlayerConditionsFeature> {

    public PlayerConditionsFeature bCF;

    public PlayerConditionsFeatureEditor(PlayerConditionsFeature bCF) {
        super("&lEntity Conditions Editor", 3*9);
        this.bCF = bCF.clone();
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(PlayerConditionFeature condition : bCF.getConditions()) {
            condition.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PlayerConditionsFeature getParent() {
        return bCF;
    }
}
