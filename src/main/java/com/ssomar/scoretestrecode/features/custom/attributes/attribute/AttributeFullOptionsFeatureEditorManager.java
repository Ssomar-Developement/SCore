package com.ssomar.scoretestrecode.features.custom.attributes.attribute;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class AttributeFullOptionsFeatureEditorManager extends FeatureEditorManagerAbstract<AttributeFullOptionsFeatureEditor, AttributeFullOptionsFeature> {

    private static AttributeFullOptionsFeatureEditorManager instance;

    public static AttributeFullOptionsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new AttributeFullOptionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public AttributeFullOptionsFeatureEditor buildEditor(AttributeFullOptionsFeature parent) {
        return new AttributeFullOptionsFeatureEditor(parent.clone(parent.getParent()));
    }

}
