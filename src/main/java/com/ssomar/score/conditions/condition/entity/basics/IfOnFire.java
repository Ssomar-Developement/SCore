package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfOnFire extends EntityCondition<Boolean, String> {


    public IfOnFire() {
        super(ConditionType.BOOLEAN, "ifOnFire", "If on fire", new String[]{}, false, " &cThe entity must be on fire to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp()) && !entity.isVisualFire()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
