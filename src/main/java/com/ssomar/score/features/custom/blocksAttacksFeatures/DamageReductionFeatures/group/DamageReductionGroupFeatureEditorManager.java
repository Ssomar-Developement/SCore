package com.ssomar.score.features.custom.blocksAttacksFeatures.DamageReductionFeatures.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DamageReductionGroupFeatureEditorManager extends FeatureEditorManagerAbstract<DamageReductionGroupFeatureEditor, DamageReductionGroupFeature> {

    private static DamageReductionGroupFeatureEditorManager instance;

    public static DamageReductionGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new DamageReductionGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public DamageReductionGroupFeatureEditor buildEditor(DamageReductionGroupFeature parent) {
        return new DamageReductionGroupFeatureEditor(parent);
    }

}
