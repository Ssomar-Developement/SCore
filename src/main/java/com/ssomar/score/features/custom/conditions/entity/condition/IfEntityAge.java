package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Entity;

public class IfEntityAge extends EntityConditionFeature<NumberConditionFeature, IfEntityAge> {

    public IfEntityAge(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityAge);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            int age = entity.getTicksLived();

            if (!StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), age)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfEntityAge getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifEntityAge));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfEntityAge getNewInstance(FeatureParentInterface parent) {
        return new IfEntityAge(parent);
    }
}
