package com.ssomar.score.features.custom.directional;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DirectionalFeaturesEditor extends FeatureEditorInterface<DirectionalFeatures> {

    public DirectionalFeatures directionalFeatures;

    public DirectionalFeaturesEditor(DirectionalFeatures containerFeatures) {
        super("&lDirectional features Editor", 3 * 9);
        this.directionalFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        directionalFeatures.getForceBlockFaceOnPlace().initAndUpdateItemParentEditor(this, i);
        i++;
        directionalFeatures.getBlockFaceOnPlace().initAndUpdateItemParentEditor(this, i);


        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DirectionalFeatures getParent() {
        return directionalFeatures;
    }
}
