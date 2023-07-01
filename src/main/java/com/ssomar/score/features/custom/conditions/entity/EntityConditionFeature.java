package com.ssomar.score.features.custom.conditions.entity;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;

public abstract class EntityConditionFeature<Y extends FeatureAbstract, T extends EntityConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public EntityConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(EntityConditionRequest request);

}


