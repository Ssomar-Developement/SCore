package com.ssomar.scoretestrecode.features.custom.conditions.placeholders.placeholder;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PlaceholderConditionFeatureEditorManager extends FeatureEditorManagerAbstract<PlaceholderConditionFeatureEditor, PlaceholderConditionFeature> {

    private static PlaceholderConditionFeatureEditorManager instance;

    public static PlaceholderConditionFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new PlaceholderConditionFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PlaceholderConditionFeatureEditor buildEditor(PlaceholderConditionFeature parent) {
        return new PlaceholderConditionFeatureEditor(parent.clone(parent.getParent()));
    }

}
