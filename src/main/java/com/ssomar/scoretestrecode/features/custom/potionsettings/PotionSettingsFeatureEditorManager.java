package com.ssomar.scoretestrecode.features.custom.potionsettings;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PotionSettingsFeatureEditorManager extends FeatureEditorManagerAbstract<PotionSettingsFeatureEditor, PotionSettingsFeature> {

    private static PotionSettingsFeatureEditorManager instance;

    public static PotionSettingsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new PotionSettingsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PotionSettingsFeatureEditor buildEditor(PotionSettingsFeature parent) {
        return new PotionSettingsFeatureEditor(parent.clone());
    }

}
