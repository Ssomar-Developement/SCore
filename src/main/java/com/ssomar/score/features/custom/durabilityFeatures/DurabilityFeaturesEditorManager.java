package com.ssomar.score.features.custom.durabilityFeatures;

import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DurabilityFeaturesEditorManager extends FeatureEditorManagerAbstract<DurabilityFeaturesEditor, DurabilityFeatures> {

    private static DurabilityFeaturesEditorManager instance;

    public static DurabilityFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new DurabilityFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public DurabilityFeaturesEditor buildEditor(DurabilityFeatures parent) {
        return new DurabilityFeaturesEditor(parent.clone(parent.getParent()));
    }

}
