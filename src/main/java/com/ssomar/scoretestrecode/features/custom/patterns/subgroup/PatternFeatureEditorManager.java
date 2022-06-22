package com.ssomar.scoretestrecode.features.custom.patterns.subgroup;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PatternFeatureEditorManager extends FeatureEditorManagerAbstract<PatternFeatureEditor, PatternFeature> {

    private static PatternFeatureEditorManager instance;

    public static PatternFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new PatternFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PatternFeatureEditor buildEditor(PatternFeature parent) {
        return new PatternFeatureEditor(parent);
    }

}
