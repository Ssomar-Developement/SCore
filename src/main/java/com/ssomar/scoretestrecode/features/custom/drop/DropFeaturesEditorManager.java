package com.ssomar.scoretestrecode.features.custom.drop;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class DropFeaturesEditorManager extends FeatureEditorManagerAbstract<DropFeaturesEditor, DropFeatures> {

    private static DropFeaturesEditorManager instance;

    public static DropFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new DropFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public DropFeaturesEditor buildEditor(DropFeatures parent) {
        return new DropFeaturesEditor(parent.clone(parent.getParent()));
    }

}
