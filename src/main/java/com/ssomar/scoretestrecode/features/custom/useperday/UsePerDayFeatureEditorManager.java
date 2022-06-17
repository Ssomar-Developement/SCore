package com.ssomar.scoretestrecode.features.custom.useperday;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class UsePerDayFeatureEditorManager extends FeatureEditorManagerAbstract<UsePerDayFeatureEditor, UsePerDayFeature> {

    private static UsePerDayFeatureEditorManager instance;

    public static UsePerDayFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new UsePerDayFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public UsePerDayFeatureEditor buildEditor(UsePerDayFeature parent) {
        return new UsePerDayFeatureEditor(parent.clone());
    }

}
