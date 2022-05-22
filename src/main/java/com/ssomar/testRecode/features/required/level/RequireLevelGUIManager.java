package com.ssomar.testRecode.features.required.level;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerInterface;

public class RequireLevelGUIManager extends FeatureEditorManagerInterface<RequireLevelGUI, RequiredLevel> {

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
