package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;

public class IfFrozen extends EntityConditionFeature<BooleanFeature, IfFrozen> {

    public IfFrozen(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifFrozen);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && SCore.is1v18Plus() && !entity.isFrozen()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfFrozen getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifFrozen, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfFrozen getNewInstance(FeatureParentInterface parent) {
        return new IfFrozen(parent);
    }
}
