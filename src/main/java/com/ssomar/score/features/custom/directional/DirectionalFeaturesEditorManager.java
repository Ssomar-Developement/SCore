package com.ssomar.score.features.custom.directional;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class DirectionalFeaturesEditorManager extends FeatureEditorManagerAbstract<DirectionalFeaturesEditor, DirectionalFeatures> {

    private static DirectionalFeaturesEditorManager instance;

    public static DirectionalFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new DirectionalFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public DirectionalFeaturesEditor buildEditor(DirectionalFeatures parent) {
        return new DirectionalFeaturesEditor(parent);
    }

}
