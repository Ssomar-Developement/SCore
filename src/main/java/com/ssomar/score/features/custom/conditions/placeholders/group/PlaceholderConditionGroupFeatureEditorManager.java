package com.ssomar.score.features.custom.conditions.placeholders.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class PlaceholderConditionGroupFeatureEditorManager extends FeatureEditorManagerAbstract<PlaceholderConditionGroupFeatureEditor, PlaceholderConditionGroupFeature> {

    private static PlaceholderConditionGroupFeatureEditorManager instance;

    public static PlaceholderConditionGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new PlaceholderConditionGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PlaceholderConditionGroupFeatureEditor buildEditor(PlaceholderConditionGroupFeature parent) {
        return new PlaceholderConditionGroupFeatureEditor(parent);
    }

}
