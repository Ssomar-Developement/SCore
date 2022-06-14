package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlayerFoodLevel extends PlayerConditionFeature<NumberConditionFeature, IfPlayerFoodLevel> {

    public IfPlayerFoodLevel(FeatureParentInterface parent) {
        super(parent, "ifPlayerFoodLevel", "If player food level", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue().get(), player.getFoodLevel())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfPlayerFoodLevel getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), "ifPlayerFoodLevel", "If player food level", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerFoodLevel getNewInstance() {
        return new IfPlayerFoodLevel(getParent());
    }
}
