package com.ssomar.score.features.custom.patterns.subpattern;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class SubPatternFeatureEditorManager extends FeatureEditorManagerAbstract<SubPatternFeatureEditor, SubPatternFeature> {

    private static SubPatternFeatureEditorManager instance;

    public static SubPatternFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new SubPatternFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public SubPatternFeatureEditor buildEditor(SubPatternFeature parent) {
        return new SubPatternFeatureEditor(parent.clone(parent.getParent()));
    }

}
