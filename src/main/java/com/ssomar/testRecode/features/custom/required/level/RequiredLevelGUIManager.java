package com.ssomar.testRecode.features.custom.required.level;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredLevelGUIManager extends FeatureEditorManagerAbstract<RequiredLevelGUI, RequiredLevel> {

    private static RequiredLevelGUIManager instance;

    public static RequiredLevelGUIManager getInstance(){
        if(instance == null){
            instance = new RequiredLevelGUIManager();
        }
        return instance;
    }

    @Override
    public RequiredLevelGUI buildEditor(RequiredLevel parent) {
        return new RequiredLevelGUI(parent.clone());
    }
}
