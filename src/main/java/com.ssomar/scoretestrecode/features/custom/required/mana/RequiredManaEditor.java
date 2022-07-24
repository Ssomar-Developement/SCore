package com.ssomar.scoretestrecode.features.custom.required.mana;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class RequiredManaEditor extends FeatureEditorInterface<RequiredMana> {

    public RequiredMana requiredMana;

    public RequiredManaEditor(RequiredMana requiredMana) {
        super("&lRequired Mana Editor", 3 * 9);
        this.requiredMana = requiredMana.clone(requiredMana.getParent());
        load();
    }

    @Override
    public void load() {
        requiredMana.getMana().initAndUpdateItemParentEditor(this, 0);
        requiredMana.getCancelEventIfError().initAndUpdateItemParentEditor(this, 1);
        requiredMana.getErrorMessage().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredMana getParent() {
        return requiredMana;
    }
}
