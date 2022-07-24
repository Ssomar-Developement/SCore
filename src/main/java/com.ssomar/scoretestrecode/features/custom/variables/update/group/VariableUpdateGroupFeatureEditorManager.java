package com.ssomar.scoretestrecode.features.custom.variables.update.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class VariableUpdateGroupFeatureEditorManager extends FeatureEditorManagerAbstract<VariableUpdateGroupFeatureEditor, VariableUpdateGroupFeature> {

    private static VariableUpdateGroupFeatureEditorManager instance;

    public static VariableUpdateGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new VariableUpdateGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public VariableUpdateGroupFeatureEditor buildEditor(VariableUpdateGroupFeature parent) {
        return new VariableUpdateGroupFeatureEditor(parent);
    }

}
