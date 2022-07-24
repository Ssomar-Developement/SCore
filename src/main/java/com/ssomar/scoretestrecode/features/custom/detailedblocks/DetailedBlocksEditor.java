package com.ssomar.scoretestrecode.features.custom.detailedblocks;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class DetailedBlocksEditor extends FeatureEditorInterface<DetailedBlocks> {

    public DetailedBlocks dropFeatures;

    public DetailedBlocksEditor(DetailedBlocks dropFeatures) {
        super("&lDetailed Blocks Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getBlocks().initAndUpdateItemParentEditor(this, 0);
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
    public DetailedBlocks getParent() {
        return dropFeatures;
    }
}
