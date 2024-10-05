package com.ssomar.score.features.custom.book;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class BookFeaturesEditor extends FeatureEditorInterface<BookFeatures> {

    public final BookFeatures dropFeatures;

    public BookFeaturesEditor(BookFeatures dropFeatures) {
        super("&lBook features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getEnable().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getTitle().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getAuthor().initAndUpdateItemParentEditor(this, 2);
        dropFeatures.getPages().initAndUpdateItemParentEditor(this, 3);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public BookFeatures getParent() {
        return dropFeatures;
    }
}
