package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerEXP extends PlayerCondition<String, String> {


    public IfPlayerEXP() {
        super(ConditionType.NUMBER_CONDITION, "ifPlayerEXP", "If player EXP", new String[]{}, "", " &cYour EXP is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined() && !StringCalculation.calculation(getAllCondition(messageSender.getSp()), player.getTotalExperience())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
