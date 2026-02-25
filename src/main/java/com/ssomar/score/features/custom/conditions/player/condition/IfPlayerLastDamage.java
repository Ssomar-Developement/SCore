package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerLastDamage extends PlayerConditionFeature<NumberConditionFeature, IfPlayerLastDamage> {

    public IfPlayerLastDamage(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerLastDamage);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            double lastDamage = player.getLastDamage();

            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), lastDamage)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerLastDamage getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifPlayerLastDamage));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerLastDamage getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerLastDamage(parent);
    }
}
