package com.ssomar.score.features.custom.bannersettings;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class BannerSettingsFeatureEditor extends FeatureEditorInterface<BannerSettingsFeature> {

    public final BannerSettingsFeature dropFeatures;

    public BannerSettingsFeatureEditor(BannerSettingsFeature dropFeatures) {
        super("&lBanner Settings Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getPatterns().initAndUpdateItemParentEditor(this, 0);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public BannerSettingsFeature getParent() {
        return dropFeatures;
    }
}
