package com.ssomar.testRecode.features.custom.enchantments.group;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class EnchantmentsGroupFeatureEditorManager extends FeatureEditorManagerAbstract<EnchantmentsGroupFeatureEditor, EnchantmentsGroupFeature> {

    private static EnchantmentsGroupFeatureEditorManager instance;

    public static EnchantmentsGroupFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new EnchantmentsGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public EnchantmentsGroupFeatureEditor buildEditor(EnchantmentsGroupFeature parent) {
        return new EnchantmentsGroupFeatureEditor(parent);
    }

}
