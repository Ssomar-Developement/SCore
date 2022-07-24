package com.ssomar.scoretestrecode.features.custom.headfeatures;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class HeadFeaturesEditorManager extends FeatureEditorManagerAbstract<HeadFeaturesEditor, HeadFeatures> {

    private static HeadFeaturesEditorManager instance;

    public static HeadFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new HeadFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public HeadFeaturesEditor buildEditor(HeadFeatures parent) {
        return new HeadFeaturesEditor(parent.clone(parent.getParent()));
    }

}
