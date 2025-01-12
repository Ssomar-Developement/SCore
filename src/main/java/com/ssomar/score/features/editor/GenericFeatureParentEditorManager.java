package com.ssomar.score.features.editor;


import com.ssomar.score.features.FeatureParentInterface;

public class GenericFeatureParentEditorManager extends FeatureEditorManagerAbstract<GenericFeatureParentEditor, FeatureParentInterface> {

    private static GenericFeatureParentEditorManager instance;

    public static GenericFeatureParentEditorManager getInstance() {
        if (instance == null) {
            instance = new GenericFeatureParentEditorManager();
        }
        return instance;
    }

    @Override
    public GenericFeatureParentEditor buildEditor(FeatureParentInterface parent) {
        return new GenericFeatureParentEditor(parent.cloneParent(parent.getParent()));
    }
}
