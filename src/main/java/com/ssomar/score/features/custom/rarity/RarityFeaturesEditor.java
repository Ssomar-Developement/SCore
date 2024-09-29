package com.ssomar.score.features.custom.rarity;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RarityFeaturesEditor extends FeatureEditorInterface<RarityFeatures> {

    public final RarityFeatures dropFeatures;

    public RarityFeaturesEditor(RarityFeatures dropFeatures) {
        super("&lItem rarity Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getEnableRarity().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getItemRarity().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RarityFeatures getParent() {
        return dropFeatures;
    }
}
