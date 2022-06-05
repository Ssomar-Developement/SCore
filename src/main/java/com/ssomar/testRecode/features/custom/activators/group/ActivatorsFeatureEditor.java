package com.ssomar.testRecode.features.custom.activators.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;
import com.ssomar.testRecode.features.custom.activators.activator.NewSActivator;

public class ActivatorsFeatureEditor extends FeatureEditorInterface<ActivatorsFeature> {

    public ActivatorsFeature activatorsGroupFeature;

    public ActivatorsFeatureEditor(ActivatorsFeature enchantsGroupFeature) {
        super("&lActivators feature Editor", 3*9);
        this.activatorsGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(NewSActivator activator : activatorsGroupFeature.getActivators().values()) {
            activator.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new activator");
    }

    @Override
    public ActivatorsFeature getParent() {
        return activatorsGroupFeature;
    }
}
