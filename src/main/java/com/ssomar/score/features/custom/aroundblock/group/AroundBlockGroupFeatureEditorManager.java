package com.ssomar.score.features.custom.aroundblock.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

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
