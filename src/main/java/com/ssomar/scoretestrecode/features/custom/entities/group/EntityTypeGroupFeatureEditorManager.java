package com.ssomar.scoretestrecode.features.custom.entities.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class EntityTypeGroupFeatureEditorManager extends FeatureEditorManagerAbstract<EntityTypeGroupFeatureEditor, EntityTypeGroupFeature> {

    private static EntityTypeGroupFeatureEditorManager instance;

    public static EntityTypeGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new EntityTypeGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public EntityTypeGroupFeatureEditor buildEditor(EntityTypeGroupFeature parent) {
        return new EntityTypeGroupFeatureEditor(parent);
    }

}
