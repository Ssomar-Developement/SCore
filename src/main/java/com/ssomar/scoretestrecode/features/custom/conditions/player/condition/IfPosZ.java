package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPosZ extends PlayerCondition<String, String> {


    public IfPosZ() {
        super(ConditionType.NUMBER_CONDITION, "ifPosZ", "If poisition Z", new String[]{}, "",  " &cCoordinate Z is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined() && !StringCalculation.calculation(getAllCondition(messageSender.getSp()), player.getLocation().getZ())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
