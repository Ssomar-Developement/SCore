package com.ssomar.score.features.custom.chiseledbookshelf;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ChiseledBookshelfFeaturesEditor extends FeatureEditorInterface<ChiseledBookshelfFeatures> {

    public ChiseledBookshelfFeatures chiseledBookshelfFeatures;

    public ChiseledBookshelfFeaturesEditor(ChiseledBookshelfFeatures containerFeatures) {
        super("&lChiseled Bookshelf features Editor", 3 * 9);
        this.chiseledBookshelfFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        chiseledBookshelfFeatures.getOccupiedSlots().initAndUpdateItemParentEditor(this, i);


        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ChiseledBookshelfFeatures getParent() {
        return chiseledBookshelfFeatures;
    }
}
