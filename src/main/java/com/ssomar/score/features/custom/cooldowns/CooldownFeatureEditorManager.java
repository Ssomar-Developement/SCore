package com.ssomar.score.features.custom.cooldowns;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class CooldownFeatureEditorManager extends FeatureEditorManagerAbstract<CooldownFeatureEditor, CooldownFeature> {

    private static CooldownFeatureEditorManager instance;

    public static CooldownFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new CooldownFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public CooldownFeatureEditor buildEditor(CooldownFeature parent) {
        return new CooldownFeatureEditor(parent.clone(parent.getParent()));
    }

}
