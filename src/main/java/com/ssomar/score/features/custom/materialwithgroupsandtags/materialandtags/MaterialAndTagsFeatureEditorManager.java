package com.ssomar.score.features.custom.materialwithgroupsandtags.materialandtags;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class MaterialAndTagsFeatureEditorManager extends FeatureEditorManagerAbstract<MaterialAndTagsFeatureEditor, MaterialAndTagsFeature> {

    private static MaterialAndTagsFeatureEditorManager instance;

    public static MaterialAndTagsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new MaterialAndTagsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public MaterialAndTagsFeatureEditor buildEditor(MaterialAndTagsFeature parent) {
        return new MaterialAndTagsFeatureEditor(parent.clone(parent.getParent()));
    }

}
