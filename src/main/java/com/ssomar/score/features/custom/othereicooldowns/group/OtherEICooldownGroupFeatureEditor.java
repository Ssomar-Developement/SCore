package com.ssomar.score.features.custom.othereicooldowns.group;

import com.ssomar.score.features.custom.othereicooldowns.cooldown.OtherEICooldown;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class OtherEICooldownGroupFeatureEditor extends FeatureEditorInterface<OtherEICooldownGroupFeature> {

    public OtherEICooldownGroupFeature attributesGroupFeature;

    public OtherEICooldownGroupFeatureEditor(OtherEICooldownGroupFeature enchantsGroupFeature) {
        super("&lOther EI Cooldowns feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (OtherEICooldown enchantment : attributesGroupFeature.getAttributes().values()) {
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
    public OtherEICooldownGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
