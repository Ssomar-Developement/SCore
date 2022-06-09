package com.ssomar.scoretestrecode.features.custom.materialwithgroupsandtags.group;


import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;

public class MaterialAndTagsGroupFeatureEditorManager extends FeatureEditorManagerAbstract<MaterialAndTagsGroupFeatureEditor, MaterialAndTagsGroupFeature> {

    private static MaterialAndTagsGroupFeatureEditorManager instance;

    public static MaterialAndTagsGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new MaterialAndTagsGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public MaterialAndTagsGroupFeatureEditor buildEditor(MaterialAndTagsGroupFeature parent) {
        return new MaterialAndTagsGroupFeatureEditor(parent);
    }

}
