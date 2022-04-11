package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfSprinting extends PlayerCondition<Boolean> {


    public IfSprinting() {
        super(ConditionType.BOOLEAN, "ifSprinting", "If sprinting", new String[]{}, false, " &cYou must sprint to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() && !player.isSprinting()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
