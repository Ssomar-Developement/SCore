package com.ssomar.scoretestrecode.features.custom.required.mana;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

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
