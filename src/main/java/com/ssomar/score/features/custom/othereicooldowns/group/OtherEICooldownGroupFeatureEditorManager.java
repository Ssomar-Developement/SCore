package com.ssomar.score.features.custom.othereicooldowns.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class OtherEICooldownGroupFeatureEditorManager extends FeatureEditorManagerAbstract<OtherEICooldownGroupFeatureEditor, OtherEICooldownGroupFeature> {

    private static OtherEICooldownGroupFeatureEditorManager instance;

    public static OtherEICooldownGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new OtherEICooldownGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public OtherEICooldownGroupFeatureEditor buildEditor(OtherEICooldownGroupFeature parent) {
        return new OtherEICooldownGroupFeatureEditor(parent);
    }

}
