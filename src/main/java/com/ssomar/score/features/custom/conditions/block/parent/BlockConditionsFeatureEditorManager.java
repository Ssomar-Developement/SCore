package com.ssomar.score.features.custom.conditions.block.parent;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class BlockConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<BlockConditionsFeatureEditor, BlockConditionsFeature> {

    private static BlockConditionsFeatureEditorManager instance;

    public static BlockConditionsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new BlockConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public BlockConditionsFeatureEditor buildEditor(BlockConditionsFeature parent) {
        return new BlockConditionsFeatureEditor(parent.clone(parent.getParent()));
    }

}
