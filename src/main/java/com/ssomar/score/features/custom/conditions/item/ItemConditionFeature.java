package com.ssomar.score.features.custom.conditions.item;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;

public abstract class ItemConditionFeature<Y extends FeatureAbstract, T extends ItemConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public ItemConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(ItemConditionRequest request);

}


