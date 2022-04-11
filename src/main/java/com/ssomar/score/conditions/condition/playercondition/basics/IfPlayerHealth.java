package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerHealth extends PlayerCondition<String> {


    public IfPlayerHealth() {
        super(ConditionType.NUMBER_CONDITION, "ifPlayerHealth", "If player health", new String[]{}, "", " &cYour health is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().length() > 0 && !StringCalculation.calculation(getCondition(), player.getHealth())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
