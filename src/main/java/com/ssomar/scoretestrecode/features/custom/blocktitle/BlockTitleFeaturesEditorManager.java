package com.ssomar.scoretestrecode.features.custom.blocktitle;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class BlockTitleFeaturesEditorManager extends FeatureEditorManagerAbstract<BlockTitleFeaturesEditor, BlockTitleFeatures> {

    private static BlockTitleFeaturesEditorManager instance;

    public static BlockTitleFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new BlockTitleFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public BlockTitleFeaturesEditor buildEditor(BlockTitleFeatures parent) {
        return new BlockTitleFeaturesEditor(parent.clone(parent.getParent()));
    }

}
