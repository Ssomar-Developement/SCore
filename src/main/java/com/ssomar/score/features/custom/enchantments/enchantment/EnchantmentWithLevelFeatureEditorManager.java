package com.ssomar.score.features.custom.enchantments.enchantment;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class EnchantmentWithLevelFeatureEditorManager extends FeatureEditorManagerAbstract<EnchantmentWithLevelFeatureEditor, EnchantmentWithLevelFeature> {

    private static EnchantmentWithLevelFeatureEditorManager instance;

    public static EnchantmentWithLevelFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new EnchantmentWithLevelFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public EnchantmentWithLevelFeatureEditor buildEditor(EnchantmentWithLevelFeature parent) {
        return new EnchantmentWithLevelFeatureEditor(parent.clone(parent.getParent()));
    }

}
