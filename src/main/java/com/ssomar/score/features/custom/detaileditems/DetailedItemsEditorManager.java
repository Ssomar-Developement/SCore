package com.ssomar.score.features.custom.detaileditems;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class DetailedItemsEditorManager extends FeatureEditorManagerAbstract<DetailedItemsEditor, DetailedItems> {

    private static DetailedItemsEditorManager instance;

    public static DetailedItemsEditorManager getInstance() {
        if (instance == null) {
            instance = new DetailedItemsEditorManager();
        }
        return instance;
    }

    @Override
    public DetailedItemsEditor buildEditor(DetailedItems parent) {
        return new DetailedItemsEditor(parent.clone(parent.getParent()));
    }

}
