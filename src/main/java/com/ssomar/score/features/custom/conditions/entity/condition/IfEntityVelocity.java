package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class IfEntityVelocity extends EntityConditionFeature<NumberConditionFeature, IfEntityVelocity> {

    public IfEntityVelocity(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityVelocity);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            Vector velocity = entity.getVelocity();
            double velocityMagnitude = velocity.length();

            if (!StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), velocityMagnitude)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfEntityVelocity getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifEntityVelocity));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfEntityVelocity getNewInstance(FeatureParentInterface parent) {
        return new IfEntityVelocity(parent);
    }
}
