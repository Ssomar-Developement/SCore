package com.ssomar.score.features.custom.variables.base.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class VariablesGroupFeatureEditorManager extends FeatureEditorManagerAbstract<VariablesGroupFeatureEditor, VariablesGroupFeature> {

    private static VariablesGroupFeatureEditorManager instance;

    public static VariablesGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new VariablesGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public VariablesGroupFeatureEditor buildEditor(VariablesGroupFeature parent) {
        return new VariablesGroupFeatureEditor(parent);
    }

}
