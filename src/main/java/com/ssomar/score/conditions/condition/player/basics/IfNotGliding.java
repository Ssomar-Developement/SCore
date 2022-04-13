package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNotGliding extends PlayerCondition<Boolean, String> {


    public IfNotGliding() {
        super(ConditionType.BOOLEAN, "ifNotGliding", "If not gliding", new String[]{}, false, " &cYou must not glide to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getAllCondition(messageSender.getSp()) && player.isGliding()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
