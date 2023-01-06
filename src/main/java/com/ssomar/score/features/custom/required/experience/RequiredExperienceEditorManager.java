package com.ssomar.score.features.custom.required.experience;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class RequiredExperienceEditorManager extends FeatureEditorManagerAbstract<RequiredExperienceEditor, RequiredExperience> {

    private static RequiredExperienceEditorManager instance;

    public static RequiredExperienceEditorManager getInstance() {
        if (instance == null) {
            instance = new RequiredExperienceEditorManager();
        }
        return instance;
    }

    @Override
    public RequiredExperienceEditor buildEditor(RequiredExperience parent) {
        return new RequiredExperienceEditor(parent.clone(parent.getParent()));
    }
}
