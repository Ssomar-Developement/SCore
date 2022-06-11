package com.ssomar.scoretestrecode.features.custom.conditions.item.parent;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class ItemConditionsFeatureEditorManager extends FeatureEditorManagerAbstract<ItemConditionsFeatureEditor, ItemConditionsFeature> {

    private static ItemConditionsFeatureEditorManager instance;

    public static ItemConditionsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new ItemConditionsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ItemConditionsFeatureEditor buildEditor(ItemConditionsFeature parent) {
        return new ItemConditionsFeatureEditor(parent.clone());
    }

}
