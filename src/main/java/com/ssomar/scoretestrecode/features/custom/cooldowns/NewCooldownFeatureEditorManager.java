package com.ssomar.scoretestrecode.features.custom.cooldowns;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class NewCooldownFeatureEditorManager extends FeatureEditorManagerAbstract<NewCooldownFeatureEditor, NewCooldownFeature> {

    private static NewCooldownFeatureEditorManager instance;

    public static NewCooldownFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new NewCooldownFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public NewCooldownFeatureEditor buildEditor(NewCooldownFeature parent) {
        return new NewCooldownFeatureEditor(parent.clone(parent.getParent()));
    }

}
