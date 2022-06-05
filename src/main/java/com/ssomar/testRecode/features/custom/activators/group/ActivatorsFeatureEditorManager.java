package com.ssomar.testRecode.features.custom.activators.group;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class ActivatorsFeatureEditorManager extends FeatureEditorManagerAbstract<ActivatorsFeatureEditor, ActivatorsFeature> {

    private static ActivatorsFeatureEditorManager instance;

    public static ActivatorsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new ActivatorsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ActivatorsFeatureEditor buildEditor(ActivatorsFeature parent) {
        return new ActivatorsFeatureEditor(parent);
    }

}
