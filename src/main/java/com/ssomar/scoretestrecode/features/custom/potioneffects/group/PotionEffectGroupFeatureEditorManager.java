package com.ssomar.scoretestrecode.features.custom.potioneffects.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PotionEffectGroupFeatureEditorManager extends FeatureEditorManagerAbstract<PotionEffectGroupFeatureEditor, PotionEffectGroupFeature> {

    private static PotionEffectGroupFeatureEditorManager instance;

    public static PotionEffectGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new PotionEffectGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PotionEffectGroupFeatureEditor buildEditor(PotionEffectGroupFeature parent) {
        return new PotionEffectGroupFeatureEditor(parent);
    }

}
