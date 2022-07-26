package com.ssomar.score.features.custom.required.executableitems.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class RequiredExecutableItemGroupFeatureEditorManager extends FeatureEditorManagerAbstract<RequiredExecutableItemGroupFeatureEditor, RequiredExecutableItemGroupFeature> {

    private static RequiredExecutableItemGroupFeatureEditorManager instance;

    public static RequiredExecutableItemGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new RequiredExecutableItemGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredExecutableItemGroupFeatureEditor buildEditor(RequiredExecutableItemGroupFeature parent) {
        return new RequiredExecutableItemGroupFeatureEditor(parent);
    }

}
