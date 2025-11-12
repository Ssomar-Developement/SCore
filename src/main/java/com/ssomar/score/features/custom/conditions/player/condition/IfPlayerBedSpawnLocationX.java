package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerBedSpawnLocationX extends PlayerConditionFeature<NumberConditionFeature, IfPlayerBedSpawnLocationX> {

    public IfPlayerBedSpawnLocationX(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerBedSpawnLocationX);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Location bedSpawn = player.getBedSpawnLocation();

            if (bedSpawn == null) {
                runInvalidCondition(request);
                return false;
            }

            double x = bedSpawn.getX();
            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), x)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerBedSpawnLocationX getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifPlayerBedSpawnLocationX));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerBedSpawnLocationX getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerBedSpawnLocationX(parent);
    }
}
