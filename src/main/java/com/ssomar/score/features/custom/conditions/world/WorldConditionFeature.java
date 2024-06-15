package com.ssomar.score.features.custom.conditions.world;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;

public abstract class WorldConditionFeature<Y extends FeatureAbstract, T extends WorldConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public WorldConditionFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
    }


    public abstract boolean verifCondition(WorldConditionRequest request);

}


