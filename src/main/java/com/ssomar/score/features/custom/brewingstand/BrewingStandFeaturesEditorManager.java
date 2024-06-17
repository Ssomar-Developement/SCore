package com.ssomar.score.features.custom.brewingstand;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class BrewingStandFeaturesEditorManager extends FeatureEditorManagerAbstract<BrewingStandFeaturesEditor, BrewingStandFeatures> {

    private static BrewingStandFeaturesEditorManager instance;

    public static BrewingStandFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new BrewingStandFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public BrewingStandFeaturesEditor buildEditor(BrewingStandFeatures parent) {
        return new BrewingStandFeaturesEditor(parent);
    }

}
