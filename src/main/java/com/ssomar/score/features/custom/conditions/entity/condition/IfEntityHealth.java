package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IfEntityHealth extends EntityConditionFeature<NumberConditionFeature, IfEntityHealth> {


    public IfEntityHealth(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityHealth);
    }


    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (hasCondition() && entity instanceof LivingEntity) {
            LivingEntity lE = (LivingEntity) entity;
            if (!StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), lE.getHealth())) {
                runInvalidCondition(request);
                return false;
            }
        }

        return true;
    }

    @Override
    public IfEntityHealth getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifEntityHealth));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfEntityHealth getNewInstance(FeatureParentInterface parent) {
        return new IfEntityHealth(parent);
    }
}
