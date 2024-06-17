package com.ssomar.score.features.custom.container;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class ContainerFeaturesEditorManager extends FeatureEditorManagerAbstract<ContainerFeaturesEditor, ContainerFeatures> {

    private static ContainerFeaturesEditorManager instance;

    public static ContainerFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new ContainerFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public ContainerFeaturesEditor buildEditor(ContainerFeatures parent) {
        return new ContainerFeaturesEditor(parent);
    }

}
