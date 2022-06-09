package com.ssomar.scoretestrecode.features.custom.attributes.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class AttributesGroupFeatureEditorManager extends FeatureEditorManagerAbstract<AttributesGroupFeatureEditor, AttributesGroupFeature> {

    private static AttributesGroupFeatureEditorManager instance;

    public static AttributesGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new AttributesGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public AttributesGroupFeatureEditor buildEditor(AttributesGroupFeature parent) {
        return new AttributesGroupFeatureEditor(parent);
    }

}
