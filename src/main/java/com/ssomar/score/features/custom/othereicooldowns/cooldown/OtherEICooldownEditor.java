package com.ssomar.score.features.custom.othereicooldowns.cooldown;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class OtherEICooldownEditor extends FeatureEditorInterface<OtherEICooldown> {

    public OtherEICooldown otherCD;

    public OtherEICooldownEditor(OtherEICooldown oCD) {
        super("&lOtherEICooldown Editor", 3 * 9);
        this.otherCD = oCD.clone(oCD.getParent());
        load();
    }

    @Override
    public void load() {
        otherCD.getExecutableItemFeature().initAndUpdateItemParentEditor(this, 0);
        otherCD.getActivatorsList().initAndUpdateItemParentEditor(this, 1);
        otherCD.getCooldown().initAndUpdateItemParentEditor(this, 2);
        otherCD.getIsCooldownInTicks().initAndUpdateItemParentEditor(this, 3);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public OtherEICooldown getParent() {
        return otherCD;
    }
}
