package com.ssomar.score.features.custom.rarity;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class RarityFeaturesEditorManager extends FeatureEditorManagerAbstract<RarityFeaturesEditor, RarityFeatures> {

    private static RarityFeaturesEditorManager instance;

    public static RarityFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new RarityFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public RarityFeaturesEditor buildEditor(RarityFeatures parent) {
        return new RarityFeaturesEditor(parent.clone(parent.getParent()));
    }

}
