package com.ssomar.score.features.custom.autoupdate;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class AutoUpdateFeaturesEditor extends FeatureEditorInterface<AutoUpdateFeatures> {

    public final AutoUpdateFeatures dropFeatures;

    public AutoUpdateFeaturesEditor(AutoUpdateFeatures dropFeatures) {
        super("&lAuto Update Features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        dropFeatures.getAutoUpdateItem().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateName().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateLore().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateDurability().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateAttributes().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateEnchants().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateCustomModelData().initAndUpdateItemParentEditor(this, i);
        i++;
        dropFeatures.getUpdateArmorSettings().initAndUpdateItemParentEditor(this, i);
        i++;

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public AutoUpdateFeatures getParent() {
        return dropFeatures;
    }
}
