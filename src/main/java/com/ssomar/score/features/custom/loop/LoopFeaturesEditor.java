package com.ssomar.score.features.custom.loop;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class LoopFeaturesEditor extends FeatureEditorInterface<LoopFeatures> {

    public final LoopFeatures loopFeatures;

    public LoopFeaturesEditor(LoopFeatures loopFeatures) {
        super("&lLoop features Editor", 3 * 9);
        this.loopFeatures = loopFeatures.clone(loopFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(FeatureInterface feature : loopFeatures.getFeatures()) {
            if(feature instanceof FeatureAbstract)  {
                FeatureAbstract featureAbstract = (FeatureAbstract) feature;
                featureAbstract.initAndUpdateItemParentEditor(this, i);
                i++;
            }
        }


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
