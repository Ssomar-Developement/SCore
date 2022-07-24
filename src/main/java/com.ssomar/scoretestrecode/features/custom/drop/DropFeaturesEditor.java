package com.ssomar.scoretestrecode.features.custom.drop;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class DropFeaturesEditor extends FeatureEditorInterface<DropFeatures> {

    public DropFeatures dropFeatures;

    public DropFeaturesEditor(DropFeatures dropFeatures) {
        super("&lDrop features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        if(!SCore.is1v11Less()) {
            dropFeatures.getGlowDrop().initAndUpdateItemParentEditor(this, i);
            i++;
            dropFeatures.getDropColor().initAndUpdateItemParentEditor(this, i);
            i++;
        }
        dropFeatures.getDisplayNameDrop().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DropFeatures getParent() {
        return dropFeatures;
    }
}
