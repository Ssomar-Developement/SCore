package com.ssomar.score.features.custom.conditions.world.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.world.WorldConditionFeature;
import com.ssomar.score.features.custom.conditions.world.WorldConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.World;

public class IfWorldTime extends WorldConditionFeature<NumberConditionFeature, IfWorldTime> {


    public IfWorldTime(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifWorldTime);
    }

    @Override
    public boolean verifCondition(WorldConditionRequest request) {
        World world = request.getWorld();
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), world.getTime())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfWorldTime getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifWorldTime));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfWorldTime getNewInstance(FeatureParentInterface parent) {
        return new IfWorldTime(parent);
    }
}
