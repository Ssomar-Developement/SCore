package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IfNotHasAI extends EntityConditionFeature<BooleanFeature, IfNotHasAI> {

    public IfNotHasAI(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotHasAI);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifNotHasAI, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotHasAI getNewInstance(FeatureParentInterface parent) {
        return new IfNotHasAI(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && entity instanceof LivingEntity && ((LivingEntity) entity).hasAI()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfNotHasAI getValue() {
        return this;
    }
}
