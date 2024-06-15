package com.ssomar.score.features.custom.conditions.custom;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;

public abstract class CustomConditionFeature<Y extends FeatureAbstract, T extends CustomConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public CustomConditionFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSetting) {
        super(parent, featureSetting);
    }

    public abstract boolean verifCondition(CustomConditionRequest request);

}


