package com.ssomar.score.features.custom.armortrim;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ArmorTrimEditorManager extends FeatureEditorManagerAbstract<ArmorTrimEditor, ArmorTrim> {

    private static ArmorTrimEditorManager instance;

    public static ArmorTrimEditorManager getInstance() {
        if (instance == null) {
            instance = new ArmorTrimEditorManager();
        }
        return instance;
    }

    @Override
    public ArmorTrimEditor buildEditor(ArmorTrim parent) {
        return new ArmorTrimEditor(parent.clone(parent.getParent()));
    }

}
