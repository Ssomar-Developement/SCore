package com.ssomar.testRecode.features.custom.enchantments.enchantment;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class EnchantmentFeaturesEditor extends FeatureEditorInterface<EnchantmentFeatures> {

    public EnchantmentFeatures dropFeatures;

    public EnchantmentFeaturesEditor(EnchantmentFeatures dropFeatures) {
        super("&lDrop features Editor", 3*9);
        this.dropFeatures = dropFeatures.clone();
        load();
    }

    @Override
    public void load() {
        dropFeatures.getGlowDrop().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getDropColor().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getDisplayNameDrop().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public EnchantmentFeatures getParent() {
        return dropFeatures;
    }
}
