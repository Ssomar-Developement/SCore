package com.ssomar.score.features.custom.detailedeffects;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DetailedEffectsEditorManager extends FeatureEditorManagerAbstract<DetailedEffectsEditor, DetailedEffects> {

    private static DetailedEffectsEditorManager instance;

    public static DetailedEffectsEditorManager getInstance() {
        if (instance == null) {
            instance = new DetailedEffectsEditorManager();
        }
        return instance;
    }

    @Override
    public DetailedEffectsEditor buildEditor(DetailedEffects parent) {
        return new DetailedEffectsEditor(parent.clone(parent.getParent()));
    }

}
