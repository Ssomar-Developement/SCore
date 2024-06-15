package com.ssomar.score.features.custom.conditions.entity;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;

public abstract class EntityConditionFeature<Y extends FeatureAbstract, T extends EntityConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public EntityConditionFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSetting) {
        super(parent, featureSetting);
    }

    public abstract boolean verifCondition(EntityConditionRequest request);

}


