package com.ssomar.score.features.custom.conditions.block;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;

public abstract class BlockConditionFeature<Y extends FeatureAbstract, T extends BlockConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public BlockConditionFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
    }

    public abstract boolean verifCondition(BlockConditionRequest request);

}


