package com.ssomar.score.features.custom.potionsettings;

import com.ssomar.score.SCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class PotionSettingsFeatureEditor extends FeatureEditorInterface<PotionSettingsFeature> {

    public PotionSettingsFeature dropFeatures;

    public PotionSettingsFeatureEditor(PotionSettingsFeature dropFeatures) {
        super("&lPotion Settings Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        if (!SCore.is1v11Less()) {
            dropFeatures.getColor().initAndUpdateItemParentEditor(this, i);
            i++;
            dropFeatures.getPotiontype().initAndUpdateItemParentEditor(this, i);
            i++;
            dropFeatures.getPotionExtended().initAndUpdateItemParentEditor(this, i);
            i++;
            dropFeatures.getPotionUpgraded().initAndUpdateItemParentEditor(this, i);
            i++;
        }
        dropFeatures.getPotionEffects().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PotionSettingsFeature getParent() {
        return dropFeatures;
    }
}
