package com.ssomar.score.features.custom.Instrument;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class InstrumentFeaturesEditorManager extends FeatureEditorManagerAbstract<InstrumentFeaturesEditor, InstrumentFeatures> {

    private static InstrumentFeaturesEditorManager instance;

    public static InstrumentFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new InstrumentFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public InstrumentFeaturesEditor buildEditor(InstrumentFeatures parent) {
        return new InstrumentFeaturesEditor(parent.clone(parent.getParent()));
    }

}
