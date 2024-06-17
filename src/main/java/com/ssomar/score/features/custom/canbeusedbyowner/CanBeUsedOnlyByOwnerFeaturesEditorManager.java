package com.ssomar.score.features.custom.canbeusedbyowner;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class CanBeUsedOnlyByOwnerFeaturesEditorManager extends FeatureEditorManagerAbstract<CanBeUsedOnlyByOwnerFeaturesEditor, CanBeUsedOnlyByOwnerFeatures> {

    private static CanBeUsedOnlyByOwnerFeaturesEditorManager instance;

    public static CanBeUsedOnlyByOwnerFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new CanBeUsedOnlyByOwnerFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public CanBeUsedOnlyByOwnerFeaturesEditor buildEditor(CanBeUsedOnlyByOwnerFeatures parent) {
        return new CanBeUsedOnlyByOwnerFeaturesEditor(parent.clone(parent.getParent()));
    }

}
