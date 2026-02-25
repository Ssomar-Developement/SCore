package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNearbyEntityCount extends PlayerConditionFeature<NumberConditionFeature, IfNearbyEntityCount> {

    public IfNearbyEntityCount(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNearbyEntityCount);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition()) {
            double radius = 10;
            int count = player.getNearbyEntities(radius, radius, radius).size();
            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), count)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNearbyEntityCount getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifNearbyEntityCount));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfNearbyEntityCount getNewInstance(FeatureParentInterface parent) {
        return new IfNearbyEntityCount(parent);
    }
}
