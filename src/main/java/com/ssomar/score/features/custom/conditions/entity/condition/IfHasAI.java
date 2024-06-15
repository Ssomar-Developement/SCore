package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IfHasAI extends EntityConditionFeature<BooleanFeature, IfHasAI> {

    public IfHasAI(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifHasAI);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifHasAI, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfHasAI getNewInstance(FeatureParentInterface parent) {
        return new IfHasAI(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && entity instanceof LivingEntity && !((LivingEntity) entity).hasAI()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfHasAI getValue() {
        return this;
    }
}
