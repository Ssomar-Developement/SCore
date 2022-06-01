package com.ssomar.testRecode.features.custom.enchantments.enchantment;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerInterface;

public class EnchantmentWithLevelFeatureEditorManager extends FeatureEditorManagerInterface<EnchantmentWithLevelFeatureEditor, EnchantmentWithLevelFeature> {

    private static EnchantmentWithLevelFeatureEditorManager instance;

    public static EnchantmentWithLevelFeatureEditorManager getInstance(){
        if(instance == null){
            instance = new EnchantmentWithLevelFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public EnchantmentWithLevelFeatureEditor buildEditor(EnchantmentWithLevelFeature parent) {
        return new EnchantmentWithLevelFeatureEditor(parent.clone());
    }

}
