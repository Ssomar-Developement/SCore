package com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.attribute;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class HasExecutableItemFeatureEditorManager extends FeatureEditorManagerAbstract<HasExecutableItemFeatureEditor, HasExecutableItemFeature> {

    private static HasExecutableItemFeatureEditorManager instance;

    public static HasExecutableItemFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new HasExecutableItemFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public HasExecutableItemFeatureEditor buildEditor(HasExecutableItemFeature parent) {
        return new HasExecutableItemFeatureEditor(parent.clone(parent.getParent()));
    }

}
