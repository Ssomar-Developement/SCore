package com.ssomar.scoretestrecode.features.custom.aroundblock.aroundblock;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class AroundBlockFeatureEditorManager extends FeatureEditorManagerAbstract<AroundBlockFeatureEditor, AroundBlockFeature> {

    private static AroundBlockFeatureEditorManager instance;

    public static AroundBlockFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new AroundBlockFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public AroundBlockFeatureEditor buildEditor(AroundBlockFeature parent) {
        return new AroundBlockFeatureEditor(parent.clone(parent.getParent()));
    }

}
