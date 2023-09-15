package com.ssomar.score.features.custom.required.magic.group;

import com.ssomar.score.features.custom.required.magic.magic.RequiredMagicFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RequiredMagicGroupFeatureEditor extends FeatureEditorInterface<RequiredMagicGroupFeature> {

    public final RequiredMagicGroupFeature attributesGroupFeature;

    public RequiredMagicGroupFeatureEditor(RequiredMagicGroupFeature enchantsGroupFeature) {
        super("&lRequired Magics feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (RequiredMagicFeature enchantment : attributesGroupFeature.getRequiredMagics().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }
        attributesGroupFeature.getErrorMessage().initAndUpdateItemParentEditor(this, i);
        i++;
        attributesGroupFeature.getCancelEventIfError().initAndUpdateItemParentEditor(this, i);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new required Magic");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredMagicGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
