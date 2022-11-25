package com.ssomar.score.projectiles.features.visualItemFeature;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class VisualItemFeatureEditorManager extends FeatureEditorManagerAbstract<VisualItemFeatureEditor, VisualItemFeature> {

    private static VisualItemFeatureEditorManager instance;

    public static VisualItemFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new VisualItemFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public VisualItemFeatureEditor buildEditor(VisualItemFeature parent) {
        return new VisualItemFeatureEditor(parent.clone(parent.getParent()));
    }

}
