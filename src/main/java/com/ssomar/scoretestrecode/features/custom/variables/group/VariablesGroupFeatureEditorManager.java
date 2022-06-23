package com.ssomar.scoretestrecode.features.custom.variables.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class VariablesGroupFeatureEditorManager extends FeatureEditorManagerAbstract<VariablesGroupFeatureEditor, VariablesGroupFeature> {

    private static VariablesGroupFeatureEditorManager instance;

    public static VariablesGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new VariablesGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public VariablesGroupFeatureEditor buildEditor(VariablesGroupFeature parent) {
        return new VariablesGroupFeatureEditor(parent);
    }

}
