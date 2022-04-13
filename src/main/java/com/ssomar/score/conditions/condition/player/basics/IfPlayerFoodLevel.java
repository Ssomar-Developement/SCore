package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerFoodLevel extends PlayerCondition<String, String> {


    public IfPlayerFoodLevel() {
        super(ConditionType.NUMBER_CONDITION, "ifPlayerFoodLevel", "If player food level", new String[]{}, "", " &cYour food is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined() && !StringCalculation.calculation(getAllCondition(messageSender.getSp()), player.getFoodLevel())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
