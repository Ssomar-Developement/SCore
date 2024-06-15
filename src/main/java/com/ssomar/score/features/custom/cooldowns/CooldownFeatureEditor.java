package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class CooldownFeatureEditor extends FeatureEditorInterface<CooldownFeature> {

    public final CooldownFeature cooldownFeature;

    public CooldownFeatureEditor(CooldownFeature cooldownFeature) {
        super("&lCooldown feature Editor", 3 * 9);
        this.cooldownFeature = cooldownFeature.clone(cooldownFeature.getParent());
        load();
    }

    @Override
    public void load() {
        cooldownFeature.getCooldown().initAndUpdateItemParentEditor(this, 0);
        cooldownFeature.getIsCooldownInTicks().initAndUpdateItemParentEditor(this, 1);
        cooldownFeature.getDisplayCooldownMessage().initAndUpdateItemParentEditor(this, 2);
        cooldownFeature.getCooldownMessage().initAndUpdateItemParentEditor(this, 3);
        cooldownFeature.getCancelEventIfInCooldown().initAndUpdateItemParentEditor(this, 4);
        cooldownFeature.getPauseWhenOffline().initAndUpdateItemParentEditor(this, 5);
        cooldownFeature.getPausePlaceholdersConditions().initAndUpdateItemParentEditor(this, 6);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public CooldownFeature getParent() {
        return cooldownFeature;
    }
}
