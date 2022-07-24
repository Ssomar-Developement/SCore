package com.ssomar.scoretestrecode.features.custom.conditions.customei.parent;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class CustomEIConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<CustomEIConditionsFeatureEditor, CustomEIConditionsFeature> {

    private static CustomEIConditionsFeatureEditorManager instance;

    public static CustomEIConditionsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new CustomEIConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public CustomEIConditionsFeatureEditor buildEditor(CustomEIConditionsFeature parent) {
        return new CustomEIConditionsFeatureEditor(parent.clone(parent.getParent()));
    }

}
