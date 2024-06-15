package com.ssomar.score.features.custom.conditions.player;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;

public abstract class PlayerConditionFeature<Y extends FeatureAbstract, T extends PlayerConditionFeature<Y, T>> extends ConditionFeature<Y, T> {

    public PlayerConditionFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
    }

    public abstract boolean verifCondition(PlayerConditionRequest playerConditionArgs);

}


