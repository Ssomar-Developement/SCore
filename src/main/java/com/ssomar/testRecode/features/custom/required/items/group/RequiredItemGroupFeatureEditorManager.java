package com.ssomar.testRecode.features.custom.required.items.group;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredItemGroupFeatureEditorManager extends FeatureEditorManagerAbstract<RequiredItemGroupFeatureEditor, RequiredItemGroupFeature> {

    private static RequiredItemGroupFeatureEditorManager instance;

    public static RequiredItemGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new RequiredItemGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredItemGroupFeatureEditor buildEditor(RequiredItemGroupFeature parent) {
        return new RequiredItemGroupFeatureEditor(parent);
    }

}
