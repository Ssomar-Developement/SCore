package com.ssomar.score.features.custom.detaileditems;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DetailedItemsEditor extends FeatureEditorInterface<DetailedItems> {

    public final DetailedItems dropFeatures;

    public DetailedItemsEditor(DetailedItems dropFeatures) {
        super("&lDetailed Blocks Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getItems().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getMessageIfNotValid().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getCancelEventIfNotValid().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DetailedItems getParent() {
        return dropFeatures;
    }
}
