package com.ssomar.scoretestrecode.features.custom.restrictions;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class RestrictionsEditorManager extends FeatureEditorManagerAbstract<RestrictionsEditor, Restrictions> {

    private static RestrictionsEditorManager instance;

    public static RestrictionsEditorManager getInstance() {
        if (instance == null) {
            instance = new RestrictionsEditorManager();
        }
        return instance;
    }

    @Override
    public RestrictionsEditor buildEditor(Restrictions parent) {
        return new RestrictionsEditor(parent.clone(parent.getParent()));
    }

}
