package com.ssomar.scoretestrecode.features.custom.cancelevents;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class CancelEventFeaturesEditorManager extends FeatureEditorManagerAbstract<CancelEventFeaturesEditor, CancelEventFeatures> {

    private static CancelEventFeaturesEditorManager instance;

    public static CancelEventFeaturesEditorManager getInstance(){
        if(instance == null){
            instance = new CancelEventFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public CancelEventFeaturesEditor buildEditor(CancelEventFeatures parent) {
        return new CancelEventFeaturesEditor(parent.clone());
    }

}
