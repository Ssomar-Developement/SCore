package com.ssomar.testRecode.features.custom.drop;


import com.ssomar.testRecode.features.custom.required.level.RequiredLevel;
import com.ssomar.testRecode.features.editor.FeatureEditorManagerInterface;

public class DropFeaturesEditorManager extends FeatureEditorManagerInterface<DropFeaturesEditor, DropFeatures> {

    private static DropFeaturesEditorManager instance;

    public static DropFeaturesEditorManager getInstance(){
        if(instance == null){
            instance = new DropFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public DropFeaturesEditor buildEditor(DropFeatures parent) {
        return new DropFeaturesEditor(parent.clone());
    }

}
