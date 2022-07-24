package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfCursorDistance extends PlayerConditionFeature<NumberConditionFeature, IfCursorDistance> {

    public IfCursorDistance(FeatureParentInterface parent) {
        super(parent, "ifCursorDistance", "If cursor distance", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Block block = player.getTargetBlock(null, 200);
            if (block.getType().equals(Material.AIR)) return false;

            if (!StringCalculation.calculation(getCondition().getValue(Optional.of(player), messageSender.getSp()).get(), player.getLocation().distance(block.getLocation()))) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
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
        setCondition(new NumberConditionFeature(getParent(), "ifCursorDistance", "If cursor distance", new String[]{}, Material.ANVIL, false));
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
