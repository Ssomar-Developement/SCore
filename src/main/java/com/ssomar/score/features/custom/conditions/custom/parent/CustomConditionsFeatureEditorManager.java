package com.ssomar.score.features.custom.conditions.custom.parent;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class CustomConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<CustomConditionsFeatureEditor, CustomConditionsFeature> {

    private static CustomConditionsFeatureEditorManager instance;

    public static CustomConditionsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new CustomConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public CustomConditionsFeatureEditor buildEditor(CustomConditionsFeature parent) {
        return new CustomConditionsFeatureEditor(parent.clone(parent.getParent()));
    }

}
