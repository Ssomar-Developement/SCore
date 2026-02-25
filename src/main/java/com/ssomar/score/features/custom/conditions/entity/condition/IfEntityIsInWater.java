package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;

public class IfEntityIsInWater extends EntityConditionFeature<BooleanFeature, IfEntityIsInWater> {

    public IfEntityIsInWater(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityIsInWater);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && !entity.isInWater()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfEntityIsInWater getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifEntityIsInWater));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfEntityIsInWater getNewInstance(FeatureParentInterface parent) {
        return new IfEntityIsInWater(parent);
    }
}
