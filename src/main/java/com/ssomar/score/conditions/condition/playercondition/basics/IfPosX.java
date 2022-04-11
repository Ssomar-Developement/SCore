package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPosX extends PlayerCondition<String> {


    public IfPosX() {
        super(ConditionType.NUMBER_CONDITION, "ifPosX", "If poisition X", new String[]{}, "",  " &cCoordinate X is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().length() > 0 && !StringCalculation.calculation(getCondition(), player.getLocation().getX())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
