package com.ssomar.score.features.custom.armortrim;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ArmorTrimEditor extends FeatureEditorInterface<ArmorTrim> {

    public final ArmorTrim dropFeatures;

    public ArmorTrimEditor(ArmorTrim dropFeatures) {
        super("&lArmorTrim Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getEnableArmorTrim().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getTrimMaterial().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getPattern().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ArmorTrim getParent() {
        return dropFeatures;
    }
}
