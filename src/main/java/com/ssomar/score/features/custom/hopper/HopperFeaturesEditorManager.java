package com.ssomar.score.features.custom.hopper;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class HopperFeaturesEditorManager extends FeatureEditorManagerAbstract<HopperFeaturesEditor, HopperFeatures> {

    private static HopperFeaturesEditorManager instance;

    public static HopperFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new HopperFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public HopperFeaturesEditor buildEditor(HopperFeatures parent) {
        return new HopperFeaturesEditor(parent);
    }

}
