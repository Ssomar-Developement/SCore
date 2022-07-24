package com.ssomar.scoretestrecode.features.custom.entities.entity;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class EntityTypeForGroupFeatureEditorManager extends FeatureEditorManagerAbstract<EntityTypeForGroupFeatureEditor, EntityTypeForGroupFeature> {

    private static EntityTypeForGroupFeatureEditorManager instance;

    public static EntityTypeForGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new EntityTypeForGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public EntityTypeForGroupFeatureEditor buildEditor(EntityTypeForGroupFeature parent) {
        return new EntityTypeForGroupFeatureEditor(parent.clone(parent.getParent()));
    }

}
