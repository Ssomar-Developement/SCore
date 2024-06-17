package com.ssomar.score.features.custom.furnace;



import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class FurnaceFeaturesEditorManager extends FeatureEditorManagerAbstract<FurnaceFeaturesEditor, FurnaceFeatures> {

    private static FurnaceFeaturesEditorManager instance;

    public static FurnaceFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new FurnaceFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public FurnaceFeaturesEditor buildEditor(FurnaceFeatures parent) {
        return new FurnaceFeaturesEditor(parent);
    }

}
