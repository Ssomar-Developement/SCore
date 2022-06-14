package com.ssomar.scoretestrecode.features.custom.conditions.player.parent;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class PlayerConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<PlayerConditionsFeatureEditor, PlayerConditionsFeature> {

    private static PlayerConditionsFeatureEditorManager instance;

    public static PlayerConditionsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new PlayerConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public PlayerConditionsFeatureEditor buildEditor(PlayerConditionsFeature parent) {
        return new PlayerConditionsFeatureEditor(parent.clone());
    }

}
