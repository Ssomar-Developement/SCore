package com.ssomar.score.features.custom.book;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class BookFeaturesEditorManager extends FeatureEditorManagerAbstract<BookFeaturesEditor, BookFeatures> {

    private static BookFeaturesEditorManager instance;

    public static BookFeaturesEditorManager getInstance() {
        if (instance == null) {
            instance = new BookFeaturesEditorManager();
        }
        return instance;
    }

    @Override
    public BookFeaturesEditor buildEditor(BookFeatures parent) {
        return new BookFeaturesEditor(parent.clone(parent.getParent()));
    }

}
