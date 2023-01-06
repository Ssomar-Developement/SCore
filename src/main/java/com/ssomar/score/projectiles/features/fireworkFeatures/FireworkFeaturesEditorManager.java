package com.ssomar.score.projectiles.features.fireworkFeatures;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class FireworkFeaturesEditorManager extends FeatureEditorManagerAbstract<FireworkFeaturesEditor, FireworkFeatures> {

    private static FireworkFeaturesEditorManager instance;

    public static FireworkFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new FireworkFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public FireworkFeaturesEditor buildEditor(FireworkFeatures parent) {
        return new FireworkFeaturesEditor(parent.clone(parent.getParent()));
    }

}
