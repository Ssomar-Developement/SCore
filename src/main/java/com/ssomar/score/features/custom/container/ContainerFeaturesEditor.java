package com.ssomar.score.features.custom.container;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ContainerFeaturesEditor extends FeatureEditorInterface<ContainerFeatures> {

    public ContainerFeatures containerFeatures;

    public ContainerFeaturesEditor(ContainerFeatures containerFeatures) {
        super("&lContainer features Editor", 3 * 9);
        this.containerFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        containerFeatures.getWhitelistMaterials().initAndUpdateItemParentEditor(this, i);
        i++;
        containerFeatures.getBlacklistMaterials().initAndUpdateItemParentEditor(this, i);
        i++;
        containerFeatures.getIsLocked().initAndUpdateItemParentEditor(this, i);
        i++;
        containerFeatures.getLockedName().initAndUpdateItemParentEditor(this, i);
        i++;
        containerFeatures.getInventoryTitle().initAndUpdateItemParentEditor(this, i);



        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ContainerFeatures getParent() {
        return containerFeatures;
    }
}
