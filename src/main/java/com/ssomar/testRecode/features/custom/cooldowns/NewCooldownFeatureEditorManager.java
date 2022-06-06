package com.ssomar.testRecode.features.custom.cooldowns;


import com.ssomar.testRecode.features.custom.attributes.attribute.AttributeFullOptionsFeature;
import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class NewCooldownFeatureEditorManager extends FeatureEditorManagerAbstract<NewCooldownFeatureEditor, NewCooldownFeature> {

    private static NewCooldownFeatureEditorManager instance;

    public static NewCooldownFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new NewCooldownFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public NewCooldownFeatureEditor buildEditor(NewCooldownFeature parent) {
        return new NewCooldownFeatureEditor(parent.clone());
    }

}
