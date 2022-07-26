package com.ssomar.score.features.custom.conditions.entity.parent;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class EntityConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<EntityConditionsFeatureEditor, EntityConditionsFeature> {

    private static EntityConditionsFeatureEditorManager instance;

    public static EntityConditionsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new EntityConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public EntityConditionsFeatureEditor buildEditor(EntityConditionsFeature parent) {
        return new EntityConditionsFeatureEditor(parent.clone(parent.getParent()));
    }

}
