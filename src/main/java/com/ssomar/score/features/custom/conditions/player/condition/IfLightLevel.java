package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfLightLevel extends PlayerConditionFeature<NumberConditionFeature, IfLightLevel> {

    public IfLightLevel(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifLightLevel);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), player.getEyeLocation().getBlock().getLightLevel())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfLightLevel getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifLightLevel));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfLightLevel getNewInstance(FeatureParentInterface parent) {
        return new IfLightLevel(parent);
    }
}
