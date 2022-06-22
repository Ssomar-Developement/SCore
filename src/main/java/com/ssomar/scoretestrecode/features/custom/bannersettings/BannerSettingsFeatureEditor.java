package com.ssomar.scoretestrecode.features.custom.bannersettings;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class BannerSettingsFeatureEditor extends FeatureEditorInterface<BannerSettingsFeature> {

    public BannerSettingsFeature dropFeatures;

    public BannerSettingsFeatureEditor(BannerSettingsFeature dropFeatures) {
        super("&lBanner Settings Editor", 3*9);
        this.dropFeatures = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        dropFeatures.getPatterns().initAndUpdateItemParentEditor(this, 0);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public BannerSettingsFeature getParent() {
        return dropFeatures;
    }
}
