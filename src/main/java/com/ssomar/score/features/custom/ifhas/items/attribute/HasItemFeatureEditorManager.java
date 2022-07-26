package com.ssomar.score.features.custom.ifhas.items.attribute;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class HasItemFeatureEditorManager extends FeatureEditorManagerAbstract<HasItemFeatureEditor, HasItemFeature> {

    private static HasItemFeatureEditorManager instance;

    public static HasItemFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new HasItemFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public HasItemFeatureEditor buildEditor(HasItemFeature parent) {
        return new HasItemFeatureEditor(parent.clone(parent.getParent()));
    }

}
