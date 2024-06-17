package com.ssomar.score.features.custom.autoupdate;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class AutoUpdateFeaturesEditorManager extends FeatureEditorManagerAbstract<AutoUpdateFeaturesEditor, AutoUpdateFeatures> {

    private static AutoUpdateFeaturesEditorManager instance;

    public static AutoUpdateFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new AutoUpdateFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public AutoUpdateFeaturesEditor buildEditor(AutoUpdateFeatures parent) {
        return new AutoUpdateFeaturesEditor(parent.clone(parent.getParent()));
    }

}
