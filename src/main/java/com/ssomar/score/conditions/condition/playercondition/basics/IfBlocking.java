package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfBlocking extends PlayerCondition<Boolean> {


    public IfBlocking() {
        super(ConditionType.BOOLEAN, "ifBlocking", "If blocking", new String[]{}, false, " &cYou must block damage with shield to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() && !player.isBlocking()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
