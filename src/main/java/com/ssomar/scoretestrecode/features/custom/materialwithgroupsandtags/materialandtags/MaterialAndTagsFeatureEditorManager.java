package com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.materialandtags;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class MaterialAndTagsFeatureEditorManager extends FeatureEditorManagerAbstract<MaterialAndTagsFeatureEditor, MaterialAndTagsFeature> {

    private static MaterialAndTagsFeatureEditorManager instance;

    public static MaterialAndTagsFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new MaterialAndTagsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public MaterialAndTagsFeatureEditor buildEditor(MaterialAndTagsFeature parent) {
        return new MaterialAndTagsFeatureEditor(parent.clone());
    }

}
