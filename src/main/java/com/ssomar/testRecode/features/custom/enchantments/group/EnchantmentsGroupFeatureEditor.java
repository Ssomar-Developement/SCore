package com.ssomar.testRecode.features.custom.enchantments.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.testRecode.features.editor.FeatureEditorInterface;

public class EnchantmentsGroupFeatureEditor extends FeatureEditorInterface<EnchantmentsGroupFeature> {

    public EnchantmentsGroupFeature enchantsGroupFeature;

    public EnchantmentsGroupFeatureEditor(EnchantmentsGroupFeature enchantsGroupFeature) {
        super("&lEnchantments feature Editor", 3*9);
        this.enchantsGroupFeature = enchantsGroupFeature.clone();
        load();
    }

    @Override
    public void load() {
        for(int i = 0; i < enchantsGroupFeature.getEnchantments().size(); i++) {
            EnchantmentWithLevelFeature enchantment = enchantsGroupFeature.getEnchantments().get(i);
            enchantment.initAndUpdateItemParentEditor(this, i);
        }

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public EnchantmentsGroupFeature getParent() {
        return enchantsGroupFeature;
    }
}
