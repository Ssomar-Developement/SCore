package com.ssomar.score.features.custom.durabilityFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DurabilityFeaturesEditor extends FeatureEditorInterface<DurabilityFeatures> {

    public final DurabilityFeatures dropFeatures;

    public DurabilityFeaturesEditor(DurabilityFeatures dropFeatures) {
        super("&lDurability Features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        if(SCore.is1v20v5Plus()) {
            dropFeatures.getIsDurabilityBasedOnUsage().initAndUpdateItemParentEditor(this, i);
            i++;
            dropFeatures.getMaxDurability().initAndUpdateItemParentEditor(this, i);
            i++;
        }
        dropFeatures.getDurability().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DurabilityFeatures getParent() {
        return dropFeatures;
    }
}
