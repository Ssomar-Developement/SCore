package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfCursorDistance extends PlayerConditionFeature<NumberConditionFeature, IfCursorDistance> {

    public IfCursorDistance(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifCursorDistance);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Block block = player.getTargetBlock(null, 200);
            double distance;
            if (block.getType().equals(Material.AIR)) distance = 200;
            else distance = player.getLocation().distance(block.getLocation());

            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), request.getSp()).get(), distance)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfCursorDistance getValue() {
        return this;
    }


    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifCursorDistance));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfCursorDistance getNewInstance(FeatureParentInterface parent) {
        return new IfCursorDistance(parent);
    }
}
