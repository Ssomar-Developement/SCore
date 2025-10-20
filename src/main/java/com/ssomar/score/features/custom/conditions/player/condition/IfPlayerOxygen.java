package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerOxygen extends PlayerConditionFeature<NumberConditionFeature, IfPlayerOxygen> {

    public IfPlayerOxygen(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerOxygen);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            int oxygen = player.getRemainingAir();

            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), oxygen)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerOxygen getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifPlayerOxygen));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerOxygen getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerOxygen(parent);
    }
}
