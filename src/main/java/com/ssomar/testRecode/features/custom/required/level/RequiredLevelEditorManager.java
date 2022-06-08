package com.ssomar.testRecode.features.custom.required.level;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredLevelEditorManager extends FeatureEditorManagerAbstract<RequiredLevelEditor, RequiredLevel> {

    private static RequiredLevelEditorManager instance;

    public static RequiredLevelEditorManager getInstance(){
        if(instance == null){
            instance = new RequiredLevelEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredLevelEditor buildEditor(RequiredLevel parent) {
        return new RequiredLevelEditor(parent.clone());
    }
}
