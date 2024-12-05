package com.ssomar.score.features.custom.firework.explosion.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class FireworkExplosionGroupFeatureEditorManager extends FeatureEditorManagerAbstract<FireworkExplosionGroupFeatureEditor, FireworkExplosionGroupFeature> {

    private static FireworkExplosionGroupFeatureEditorManager instance;

    public static FireworkExplosionGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new FireworkExplosionGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public FireworkExplosionGroupFeatureEditor buildEditor(FireworkExplosionGroupFeature parent) {
        return new FireworkExplosionGroupFeatureEditor(parent);
    }

}
