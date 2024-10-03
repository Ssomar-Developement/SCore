package com.ssomar.score.features.custom.itemglow;



import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;


public class ItemGlowFeaturesEditorManager extends FeatureEditorManagerAbstract<ItemGlowFeaturesEditor, ItemGlowFeatures> {

    private static ItemGlowFeaturesEditorManager instance;

    public static ItemGlowFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new ItemGlowFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public ItemGlowFeaturesEditor buildEditor(ItemGlowFeatures parent) {
        return new ItemGlowFeaturesEditor(parent);
    }

}
