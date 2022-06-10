package com.ssomar.scoretestrecode.features.custom.conditions.world.block.parent;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class WorldConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<WorldConditionsFeatureEditor, WorldConditionsFeature> {

    private static WorldConditionsFeatureEditorManager instance;

    public static WorldConditionsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new WorldConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public WorldConditionsFeatureEditor buildEditor(WorldConditionsFeature parent) {
        return new WorldConditionsFeatureEditor(parent.clone());
    }

}
