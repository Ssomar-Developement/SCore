package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfFrozen extends EntityCondition<Boolean, String> {

    public IfFrozen() {
        super(ConditionType.BOOLEAN, "ifFrozen", "If frozen", new String[]{}, false, " &cThe entity must be frozen to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp()) && SCore.is1v18Plus() && !entity.isFrozen()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }

        return true;
    }
}
