package com.ssomar.scoretestrecode.features.custom.aroundblock.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class AroundBlockGroupFeatureEditorManager extends FeatureEditorManagerAbstract<AroundBlockGroupFeatureEditor, AroundBlockGroupFeature> {

    private static AroundBlockGroupFeatureEditorManager instance;

    public static AroundBlockGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new AroundBlockGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public AroundBlockGroupFeatureEditor buildEditor(AroundBlockGroupFeature parent) {
        return new AroundBlockGroupFeatureEditor(parent);
    }

}
