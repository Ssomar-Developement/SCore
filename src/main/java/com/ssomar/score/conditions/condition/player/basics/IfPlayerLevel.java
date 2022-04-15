package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerLevel extends PlayerCondition<String, String> {


    public IfPlayerLevel() {
        super(ConditionType.NUMBER_CONDITION, "ifPlayerLevel", "If player level", new String[]{}, "", " &cYour level is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined() && !StringCalculation.calculation(getAllCondition(messageSender.getSp()), player.getLevel())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
