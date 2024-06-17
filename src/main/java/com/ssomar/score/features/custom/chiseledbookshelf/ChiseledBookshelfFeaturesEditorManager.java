package com.ssomar.score.features.custom.chiseledbookshelf;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class ChiseledBookshelfFeaturesEditorManager extends FeatureEditorManagerAbstract<ChiseledBookshelfFeaturesEditor, ChiseledBookshelfFeatures> {

    private static ChiseledBookshelfFeaturesEditorManager instance;

    public static ChiseledBookshelfFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new ChiseledBookshelfFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public ChiseledBookshelfFeaturesEditor buildEditor(ChiseledBookshelfFeatures parent) {
        return new ChiseledBookshelfFeaturesEditor(parent);
    }

}
