package com.ssomar.score.features.custom.required.magic.magic;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class RequiredMagicFeatureEditorManager extends FeatureEditorManagerAbstract<RequiredMagicFeatureEditor, RequiredMagicFeature> {

    private static RequiredMagicFeatureEditorManager instance;

    public static RequiredMagicFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new RequiredMagicFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredMagicFeatureEditor buildEditor(RequiredMagicFeature parent) {
        return new RequiredMagicFeatureEditor(parent.clone(parent.getParent()));
    }

}
