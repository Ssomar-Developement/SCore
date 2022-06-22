package com.ssomar.scoretestrecode.features.custom.bannersettings;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class BannerSettingsFeatureEditorManager extends FeatureEditorManagerAbstract<BannerSettingsFeatureEditor, BannerSettingsFeature> {

    private static BannerSettingsFeatureEditorManager instance;

    public static BannerSettingsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new BannerSettingsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public BannerSettingsFeatureEditor buildEditor(BannerSettingsFeature parent) {
        return new BannerSettingsFeatureEditor(parent.clone());
    }

}
