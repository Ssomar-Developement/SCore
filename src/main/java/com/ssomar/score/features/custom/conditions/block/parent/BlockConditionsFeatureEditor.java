package com.ssomar.score.features.custom.conditions.block.parent;

import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class BlockConditionsFeatureEditor extends FeatureEditorInterface<BlockConditionsFeature> {

    public final BlockConditionsFeature bCF;

    public BlockConditionsFeatureEditor(BlockConditionsFeature dropFeatures) {
        super("&lBlock Conditions Editor", 4 * 9);
        this.bCF = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (BlockConditionFeature condition : bCF.getConditions()) {
            condition.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 27, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 28, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 35, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public BlockConditionsFeature getParent() {
        return bCF;
    }
}
