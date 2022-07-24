package com.ssomar.scoretestrecode.features.custom.patterns.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PatternsGroupFeatureEditorManager extends FeatureEditorManagerAbstract<PatternsGroupFeatureEditor, PatternsGroupFeature> {

    private static PatternsGroupFeatureEditorManager instance;

    public static PatternsGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new PatternsGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PatternsGroupFeatureEditor buildEditor(PatternsGroupFeature parent) {
        return new PatternsGroupFeatureEditor(parent);
    }

}
