package com.ssomar.score.features.custom.conditions.item;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;

public abstract class ItemConditionFeature<Y extends FeatureAbstract, T extends ItemConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public ItemConditionFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSetting) {
        super(parent, featureSetting);
    }


    public abstract boolean verifCondition(ItemConditionRequest request);

}


