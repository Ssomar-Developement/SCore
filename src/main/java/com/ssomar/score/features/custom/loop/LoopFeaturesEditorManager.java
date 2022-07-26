package com.ssomar.score.features.custom.loop;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class LoopFeaturesEditorManager extends FeatureEditorManagerAbstract<LoopFeaturesEditor, LoopFeatures> {

    private static LoopFeaturesEditorManager instance;

    public static LoopFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new LoopFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public LoopFeaturesEditor buildEditor(LoopFeatures parent) {
        return new LoopFeaturesEditor(parent.clone(parent.getParent()));
    }

}
