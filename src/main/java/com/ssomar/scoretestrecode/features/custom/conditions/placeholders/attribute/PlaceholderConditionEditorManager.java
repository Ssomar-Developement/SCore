package com.ssomar.scoretestrecode.features.custom.conditions.placeholders.attribute;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PlaceholderConditionEditorManager extends FeatureEditorManagerAbstract<PlaceholderConditionFeatureEditor, PlaceholderCondition> {

    private static PlaceholderConditionEditorManager instance;

    public static PlaceholderConditionEditorManager getInstance(){
        if(instance == null){
            instance = new PlaceholderConditionEditorManager();
        }
        return instance;
    }

    @Override
    public PlaceholderConditionFeatureEditor buildEditor(PlaceholderCondition parent) {
        return new PlaceholderConditionFeatureEditor(parent.clone());
    }

}
