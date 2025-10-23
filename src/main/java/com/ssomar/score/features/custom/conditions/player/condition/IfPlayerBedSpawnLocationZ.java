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

public class IfPlayerBedSpawnLocationZ extends PlayerConditionFeature<NumberConditionFeature, IfPlayerBedSpawnLocationZ> {

    public IfPlayerBedSpawnLocationZ(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerBedSpawnLocationZ);
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

            double z = bedSpawn.getZ();
            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), z)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerBedSpawnLocationZ getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifPlayerBedSpawnLocationZ));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerBedSpawnLocationZ getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerBedSpawnLocationZ(parent);
    }
}
