package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPosZ extends PlayerConditionFeature<NumberConditionFeature, IfPosZ> {

    public IfPosZ(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifPosZ);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), player.getLocation().getZ())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfPosZ getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifPosZ));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPosZ getNewInstance(FeatureParentInterface parent) {
        return new IfPosZ(parent);
    }
}
