package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerHealth extends PlayerConditionFeature<NumberConditionFeature, IfPlayerHealth> {

    public IfPlayerHealth(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerHealth);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), player.getHealth())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfPlayerHealth getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifPlayerHealth));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerHealth getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerHealth(parent);
    }
}
