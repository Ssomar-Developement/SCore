package com.ssomar.scoretestrecode.features.custom.potionsettings;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class PotionSettingsFeatureEditor extends FeatureEditorInterface<PotionSettingsFeature> {

    public PotionSettingsFeature dropFeatures;

    public PotionSettingsFeatureEditor(PotionSettingsFeature dropFeatures) {
        super("&lPotion Settings Editor", 3*9);
        this.dropFeatures = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        dropFeatures.getColor().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getPotiontype().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getPotionExtended().initAndUpdateItemParentEditor(this, 2);
        dropFeatures.getPotionUpgraded().initAndUpdateItemParentEditor(this, 3);
        dropFeatures.getPotionEffects().initAndUpdateItemParentEditor(this, 4);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PotionSettingsFeature getParent() {
        return dropFeatures;
    }
}
