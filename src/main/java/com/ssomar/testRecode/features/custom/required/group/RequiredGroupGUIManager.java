package com.ssomar.testRecode.features.custom.required.group;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredGroupGUIManager extends FeatureEditorManagerAbstract<RequiredGroupGUI, RequiredGroup> {

    private static RequiredGroupGUIManager instance;

    public static RequiredGroupGUIManager getInstance(){
        if(instance == null){
            instance = new RequiredGroupGUIManager();
        }
        return instance;
    }

    @Override
    public RequiredGroupGUI buildEditor(RequiredGroup parent) {
        return new RequiredGroupGUI(parent.clone());
    }
}
