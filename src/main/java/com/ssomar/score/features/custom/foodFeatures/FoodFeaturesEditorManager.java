package com.ssomar.score.features.custom.foodFeatures;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class FoodFeaturesEditorManager extends FeatureEditorManagerAbstract<FoodFeaturesEditor, FoodFeatures> {

    private static FoodFeaturesEditorManager instance;

    public static FoodFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new FoodFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public FoodFeaturesEditor buildEditor(FoodFeatures parent) {
        return new FoodFeaturesEditor(parent.clone(parent.getParent()));
    }

}
