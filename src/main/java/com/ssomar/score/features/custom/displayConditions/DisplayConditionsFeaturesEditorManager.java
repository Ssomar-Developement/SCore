package com.ssomar.score.features.custom.displayConditions;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DisplayConditionsFeaturesEditorManager extends FeatureEditorManagerAbstract<DisplayConditionsFeaturesEditor, DisplayConditionsFeatures> {

    private static DisplayConditionsFeaturesEditorManager instance;

    public static DisplayConditionsFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new DisplayConditionsFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public DisplayConditionsFeaturesEditor buildEditor(DisplayConditionsFeatures parent) {
        return new DisplayConditionsFeaturesEditor(parent.clone(parent.getParent()));
    }

}
