package com.ssomar.scoretestrecode.features.custom.required.executableitems.item;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredExecutableItemFeatureEditorManager extends FeatureEditorManagerAbstract<RequiredExecutableItemFeatureEditor, RequiredExecutableItemFeature> {

    private static RequiredExecutableItemFeatureEditorManager instance;

    public static RequiredExecutableItemFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new RequiredExecutableItemFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredExecutableItemFeatureEditor buildEditor(RequiredExecutableItemFeature parent) {
        return new RequiredExecutableItemFeatureEditor(parent.clone());
    }

}
