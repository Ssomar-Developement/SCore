package com.ssomar.score.features.custom.detailedblocks;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class DetailedBlocksEditor extends FeatureEditorInterface<DetailedBlocks> {

    public final DetailedBlocks dropFeatures;

    public DetailedBlocksEditor(DetailedBlocks dropFeatures) {
        super("&lDetailed Blocks Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getBlocks().initAndUpdateItemParentEditor(this, 0);
        if(!dropFeatures.isDisableMessageIfNotValid()) dropFeatures.getMessageIfNotValid().initAndUpdateItemParentEditor(this, 1);
        if(!dropFeatures.isDisableCancelEventIfNotValid()) dropFeatures.getCancelEventIfNotValid().initAndUpdateItemParentEditor(this, 2);

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
