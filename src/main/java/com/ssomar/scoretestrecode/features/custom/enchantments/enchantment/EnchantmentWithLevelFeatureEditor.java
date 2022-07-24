package com.ssomar.scoretestrecode.features.custom.enchantments.enchantment;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class EnchantmentWithLevelFeatureEditor extends FeatureEditorInterface<EnchantmentWithLevelFeature> {

    public EnchantmentWithLevelFeature enchantFeature;

    public EnchantmentWithLevelFeatureEditor(EnchantmentWithLevelFeature dropFeatures) {
        super("&lEnchantment feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getEnchantment().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getLevel().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public EnchantmentWithLevelFeature getParent() {
        return enchantFeature;
    }
}
