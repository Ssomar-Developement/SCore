package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;

public class IfEntityIsInLava extends EntityConditionFeature<BooleanFeature, IfEntityIsInLava> {

    public IfEntityIsInLava(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityIsInLava);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && !entity.isInLava()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfEntityIsInLava getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifEntityIsInLava));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfEntityIsInLava getNewInstance(FeatureParentInterface parent) {
        return new IfEntityIsInLava(parent);
    }
}
