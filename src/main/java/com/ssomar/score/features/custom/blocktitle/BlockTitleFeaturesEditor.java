package com.ssomar.score.features.custom.blocktitle;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class BlockTitleFeaturesEditor extends FeatureEditorInterface<BlockTitleFeatures> {

    public BlockTitleFeatures dropFeatures;

    public BlockTitleFeaturesEditor(BlockTitleFeatures dropFeatures) {
        super("&lTitle features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getActiveTitle().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getTitle().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getTitleAjustement().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public BlockTitleFeatures getParent() {
        return dropFeatures;
    }
}
