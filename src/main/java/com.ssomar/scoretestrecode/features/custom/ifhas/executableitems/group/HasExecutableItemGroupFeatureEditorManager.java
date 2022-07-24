package com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class HasExecutableItemGroupFeatureEditorManager extends FeatureEditorManagerAbstract<HasExecutableItemGroupFeatureEditor, HasExecutableItemGroupFeature> {

    private static HasExecutableItemGroupFeatureEditorManager instance;

    public static HasExecutableItemGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new HasExecutableItemGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public HasExecutableItemGroupFeatureEditor buildEditor(HasExecutableItemGroupFeature parent) {
        return new HasExecutableItemGroupFeatureEditor(parent);
    }

}
