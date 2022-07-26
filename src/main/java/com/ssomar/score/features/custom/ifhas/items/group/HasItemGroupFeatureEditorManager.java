package com.ssomar.score.features.custom.ifhas.items.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class HasItemGroupFeatureEditorManager extends FeatureEditorManagerAbstract<HasItemGroupFeatureEditor, HasItemGroupFeature> {

    private static HasItemGroupFeatureEditorManager instance;

    public static HasItemGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new HasItemGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public HasItemGroupFeatureEditor buildEditor(HasItemGroupFeature parent) {
        return new HasItemGroupFeatureEditor(parent);
    }

}
