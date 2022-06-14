package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPosY extends PlayerCondition<String, String> {


    public IfPosY() {
        super(ConditionType.NUMBER_CONDITION, "ifPosY", "If poisition Y", new String[]{}, "",  " &cCoordinate Y is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined() && !StringCalculation.calculation(getAllCondition(messageSender.getSp()), player.getLocation().getY())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
