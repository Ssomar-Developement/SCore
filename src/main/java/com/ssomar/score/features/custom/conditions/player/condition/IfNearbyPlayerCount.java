package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNearbyPlayerCount extends PlayerConditionFeature<NumberConditionFeature, IfNearbyPlayerCount> {

    public IfNearbyPlayerCount(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNearbyPlayerCount);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition()) {
            double radius = 10;
            long count = player.getNearbyEntities(radius, radius, radius).stream()
                    .filter(e -> e instanceof Player)
                    .count();
            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), count)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNearbyPlayerCount getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifNearbyPlayerCount));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfNearbyPlayerCount getNewInstance(FeatureParentInterface parent) {
        return new IfNearbyPlayerCount(parent);
    }
}
