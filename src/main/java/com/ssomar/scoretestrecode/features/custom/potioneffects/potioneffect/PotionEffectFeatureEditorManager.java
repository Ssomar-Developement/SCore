package com.ssomar.scoretestrecode.features.custom.potioneffects.potioneffect;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PotionEffectFeatureEditorManager extends FeatureEditorManagerAbstract<PotionEffectFeatureEditor, PotionEffectFeature> {

    private static PotionEffectFeatureEditorManager instance;

    public static PotionEffectFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new PotionEffectFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PotionEffectFeatureEditor buildEditor(PotionEffectFeature parent) {
        return new PotionEffectFeatureEditor(parent.clone(parent.getParent()));
    }

}
