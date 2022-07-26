package com.ssomar.score.features.custom.conditions;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ConditionFeatureEditorManager extends FeatureEditorManagerAbstract<ConditionFeatureEditor, ConditionFeature> {

    private static ConditionFeatureEditorManager instance;

    public static ConditionFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new ConditionFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ConditionFeatureEditor buildEditor(ConditionFeature parent) {
        return new ConditionFeatureEditor(parent.clone(parent.getParent()));
    }

}
