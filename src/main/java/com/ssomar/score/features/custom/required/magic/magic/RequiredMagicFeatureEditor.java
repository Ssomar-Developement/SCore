package com.ssomar.score.features.custom.required.magic.magic;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class RequiredMagicFeatureEditor extends FeatureEditorInterface<RequiredMagicFeature> {

    public final RequiredMagicFeature requiredExecutableItemFeature;

    public RequiredMagicFeatureEditor(RequiredMagicFeature dropFeatures) {
        super("&lRequired Magic feature Editor", 3 * 9);
        this.requiredExecutableItemFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        requiredExecutableItemFeature.getMagic().initAndUpdateItemParentEditor(this, 0);
        requiredExecutableItemFeature.getAmount().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public RequiredMagicFeature getParent() {
        return requiredExecutableItemFeature;
    }
}
