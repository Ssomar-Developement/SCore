package com.ssomar.testRecode.features.custom.required.level;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class RequireLevelGUIManager extends FeatureEditorManagerAbstract<RequireLevelGUI, RequiredLevel> {

    private static RequireLevelGUIManager instance;

    public static RequireLevelGUIManager getInstance(){
        if(instance == null){
            instance = new RequireLevelGUIManager();
        }
        return instance;
    }

    @Override
    public RequireLevelGUI buildEditor(RequiredLevel parent) {
        return new RequireLevelGUI(parent.clone());
    }
}
