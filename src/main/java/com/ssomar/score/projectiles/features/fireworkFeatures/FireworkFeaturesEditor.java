package com.ssomar.score.projectiles.features.fireworkFeatures;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class FireworkFeaturesEditor extends FeatureEditorInterface<FireworkFeatures> {

    public final FireworkFeatures visualItemFeature;

    public FireworkFeaturesEditor(FireworkFeatures dropFeatures) {
        super("&lFirework Features Editor", 3 * 9);
        this.visualItemFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {

        visualItemFeature.getLifeTime().initAndUpdateItemParentEditor(this, 0);
        visualItemFeature.getType().initAndUpdateItemParentEditor(this, 1);
        visualItemFeature.getColors().initAndUpdateItemParentEditor(this, 2);
        visualItemFeature.getFadeColors().initAndUpdateItemParentEditor(this, 3);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public FireworkFeatures getParent() {
        return visualItemFeature;
    }
}
