package com.ssomar.score.features.custom.detailedblocks;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DetailedBlocksEditorManager extends FeatureEditorManagerAbstract<DetailedBlocksEditor, DetailedBlocks> {

    private static DetailedBlocksEditorManager instance;

    public static DetailedBlocksEditorManager getInstance() {
        if (instance == null) {
            instance = new DetailedBlocksEditorManager();
        }
        return instance;
    }

    @Override
    public DetailedBlocksEditor buildEditor(DetailedBlocks parent) {
        return new DetailedBlocksEditor(parent.clone(parent.getParent()));
    }

}
