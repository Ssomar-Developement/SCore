package com.ssomar.score.features.custom.required.magic.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class RequiredMagicGroupFeatureEditorManager extends FeatureEditorManagerAbstract<RequiredMagicGroupFeatureEditor, RequiredMagicGroupFeature> {

    private static RequiredMagicGroupFeatureEditorManager instance;

    public static RequiredMagicGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new RequiredMagicGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredMagicGroupFeatureEditor buildEditor(RequiredMagicGroupFeature parent) {
        return new RequiredMagicGroupFeatureEditor(parent);
    }

}
