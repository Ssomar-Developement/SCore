package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNotFlying extends PlayerCondition<Boolean> {

    public IfNotFlying() {
        super(ConditionType.BOOLEAN, "ifNotFlying", "If not flying", new String[]{}, false, " &cYou must not fly to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() && player.isFlying()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
