package com.ssomar.score.features.custom.blocksAttacksFeatures.DamageReductionFeatures.group;

import com.ssomar.score.features.custom.blocksAttacksFeatures.DamageReductionFeatures.DamageReductionFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DamageReductionGroupFeatureEditor extends FeatureEditorInterface<DamageReductionGroupFeature> {

    public final DamageReductionGroupFeature enchantsGroupFeature;

    public DamageReductionGroupFeatureEditor(DamageReductionGroupFeature enchantsGroupFeature) {
        super("&lDamageReductions feature Editor", 3 * 9);
        this.enchantsGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (DamageReductionFeature enchantment : enchantsGroupFeature.getReductions().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new damageReduction");
    }

    @Override
    public DamageReductionGroupFeature getParent() {
        return enchantsGroupFeature;
    }
}
