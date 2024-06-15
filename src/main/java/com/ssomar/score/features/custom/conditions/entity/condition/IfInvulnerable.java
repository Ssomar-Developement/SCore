package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;

public class IfInvulnerable extends EntityConditionFeature<BooleanFeature, IfInvulnerable> {

    public IfInvulnerable(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifInvulnerable);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && !entity.isInvulnerable()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfInvulnerable getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifInvulnerable, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfInvulnerable getNewInstance(FeatureParentInterface parent) {
        return new IfInvulnerable(parent);
    }
}
