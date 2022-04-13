package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNotSwimming extends PlayerCondition<Boolean, String> {


    public IfNotSwimming() {
        super(ConditionType.BOOLEAN, "ifNotSwimming", "If not swimming", new String[]{}, false, " &cYou must not swin to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getAllCondition(messageSender.getSp()) && player.isSwimming()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
