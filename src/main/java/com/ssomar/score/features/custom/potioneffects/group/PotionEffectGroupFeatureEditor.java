package com.ssomar.score.features.custom.potioneffects.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.custom.potioneffects.potioneffect.PotionEffectFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class PotionEffectGroupFeatureEditor extends FeatureEditorInterface<PotionEffectGroupFeature> {

    public PotionEffectGroupFeature attributesGroupFeature;

    public PotionEffectGroupFeatureEditor(PotionEffectGroupFeature enchantsGroupFeature) {
        super("&lPotion Effects feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (PotionEffectFeature enchantment : attributesGroupFeature.getEffects().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new attribute");
    }

    @Override
    public PotionEffectGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
