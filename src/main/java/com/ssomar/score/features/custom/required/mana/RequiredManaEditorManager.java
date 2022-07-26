package com.ssomar.score.features.custom.required.mana;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class RequiredManaEditorManager extends FeatureEditorManagerAbstract<RequiredManaEditor, RequiredMana> {

    private static RequiredManaEditorManager instance;

    public static RequiredManaEditorManager getInstance() {
        if (instance == null) {
            instance = new RequiredManaEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredManaEditor buildEditor(RequiredMana parent) {
        return new RequiredManaEditor(parent.clone(parent.getParent()));
    }
}
