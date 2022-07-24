package com.ssomar.scoretestrecode.features.custom.enchantments.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class EnchantmentsGroupFeatureEditor extends FeatureEditorInterface<EnchantmentsGroupFeature> {

    public EnchantmentsGroupFeature enchantsGroupFeature;

    public EnchantmentsGroupFeatureEditor(EnchantmentsGroupFeature enchantsGroupFeature) {
        super("&lEnchantments feature Editor", 3 * 9);
        this.enchantsGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (EnchantmentWithLevelFeature enchantment : enchantsGroupFeature.getEnchantments().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new enchantment");
    }

    @Override
    public EnchantmentsGroupFeature getParent() {
        return enchantsGroupFeature;
    }
}
