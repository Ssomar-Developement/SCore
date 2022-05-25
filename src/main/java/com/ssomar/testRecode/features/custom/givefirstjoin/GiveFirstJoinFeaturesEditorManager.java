package com.ssomar.testRecode.features.custom.givefirstjoin;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerInterface;

public class GiveFirstJoinFeaturesEditorManager extends FeatureEditorManagerInterface<GiveFirstJoinFeaturesEditor, GiveFirstJoinFeatures> {

    private static GiveFirstJoinFeaturesEditorManager instance;

    public static GiveFirstJoinFeaturesEditorManager getInstance(){
        if(instance == null){
            instance = new GiveFirstJoinFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public GiveFirstJoinFeaturesEditor buildEditor(GiveFirstJoinFeatures parent) {
        return new GiveFirstJoinFeaturesEditor(parent.clone());
    }

}
