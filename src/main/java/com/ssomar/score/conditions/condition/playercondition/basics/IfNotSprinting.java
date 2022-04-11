package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNotSprinting extends PlayerCondition<Boolean> {


    public IfNotSprinting() {
        super(ConditionType.BOOLEAN, "ifNotSprinting", "If not sprinting", new String[]{}, false, " &cYou must not sprint to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() && player.isSprinting()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
