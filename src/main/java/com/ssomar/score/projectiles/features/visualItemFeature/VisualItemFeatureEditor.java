package com.ssomar.score.projectiles.features.visualItemFeature;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class VisualItemFeatureEditor extends FeatureEditorInterface<VisualItemFeature> {

    public final VisualItemFeature visualItemFeature;

    public VisualItemFeatureEditor(VisualItemFeature dropFeatures) {
        super("&lVisual Item Feature Editor", 3 * 9);
        this.visualItemFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {

        visualItemFeature.getMaterial().initAndUpdateItemParentEditor(this, 0);
        visualItemFeature.getHeadFeatures().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public VisualItemFeature getParent() {
        return visualItemFeature;
    }
}
