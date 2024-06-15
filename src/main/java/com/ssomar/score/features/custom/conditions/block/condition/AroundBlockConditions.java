package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.aroundblock.aroundblock.AroundBlockFeature;
import com.ssomar.score.features.custom.aroundblock.group.AroundBlockGroupFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;

public class AroundBlockConditions extends BlockConditionFeature<AroundBlockGroupFeature, AroundBlockConditions> {

    public AroundBlockConditions(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.aroundBlockCdts);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getAroundBlockGroup().size() > 0;
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition()) {
            for (AroundBlockFeature cdt : getCondition().getAroundBlockGroup().values()) {
                if (!cdt.verif(request.getBlock(), request.getPlayerOpt(), request.getErrors())) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public AroundBlockConditions getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new AroundBlockGroupFeature(this, true));
    }

    @Override
    public AroundBlockConditions getNewInstance(FeatureParentInterface parent) {
        return new AroundBlockConditions(parent);
    }
}
