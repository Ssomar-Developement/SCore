package com.ssomar.score.features.custom.particles.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ParticlesGroupFeatureEditorManager extends FeatureEditorManagerAbstract<ParticlesGroupFeatureEditor, ParticlesGroupFeature> {

    private static ParticlesGroupFeatureEditorManager instance;

    public static ParticlesGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new ParticlesGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ParticlesGroupFeatureEditor buildEditor(ParticlesGroupFeature parent) {
        return new ParticlesGroupFeatureEditor(parent);
    }

}
