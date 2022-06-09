package com.ssomar.testRecode.features.custom.conditions;


import com.ssomar.testRecode.features.custom.hiders.Hiders;
import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class ConditionFeatureEditorManager extends FeatureEditorManagerAbstract<ConditionFeatureEditor, ConditionFeature> {

    private static ConditionFeatureEditorManager instance;

    public static ConditionFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new ConditionFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ConditionFeatureEditor buildEditor(ConditionFeature parent) {
        return new ConditionFeatureEditor(parent.clone());
    }

}
