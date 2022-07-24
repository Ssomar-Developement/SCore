package com.ssomar.scoretestrecode.features.custom.required.parent;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredGroupEditorManager extends FeatureEditorManagerAbstract<RequiredGroupEditor, RequiredGroup> {

    private static RequiredGroupEditorManager instance;

    public static RequiredGroupEditorManager getInstance() {
        if (instance == null) {
            instance = new RequiredGroupEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredGroupEditor buildEditor(RequiredGroup parent) {
        return new RequiredGroupEditor(parent.clone(parent.getParent()));
    }
}
