package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPosY extends PlayerCondition<String> {


    public IfPosY() {
        super(ConditionType.NUMBER_CONDITION, "ifPosY", "If poisition Y", new String[]{}, "",  " &cCoordinate Y is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().length() > 0 && !StringCalculation.calculation(getCondition(), player.getLocation().getY())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
