package com.ssomar.score.features.custom.loop;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class LoopFeaturesEditor extends FeatureEditorInterface<LoopFeatures> {

    public LoopFeatures loopFeatures;

    public LoopFeaturesEditor(LoopFeatures dropFeatures) {
        super("&lLoop features Editor", 3 * 9);
        this.loopFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        loopFeatures.getDelay().initAndUpdateItemParentEditor(this, 0);
        loopFeatures.getDelayInTick().initAndUpdateItemParentEditor(this, 1);


        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public LoopFeatures getParent() {
        return loopFeatures;
    }
}
