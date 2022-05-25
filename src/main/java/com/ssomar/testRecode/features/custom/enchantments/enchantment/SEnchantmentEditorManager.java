package com.ssomar.testRecode.features.custom.enchantments.enchantment;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerInterface;

public class SEnchantmentEditorManager extends FeatureEditorManagerInterface<EnchantmentFeaturesEditor, EnchantmentFeatures> {

    private static SEnchantmentEditorManager instance;

    public static SEnchantmentEditorManager getInstance(){
        if(instance == null){
            instance = new SEnchantmentEditorManager();
        }
        return instance;
    }

    @Override
    public EnchantmentFeaturesEditor buildEditor(EnchantmentFeatures parent) {
        return new EnchantmentFeaturesEditor(parent.clone());
    }

}
