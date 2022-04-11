package com.ssomar.score.conditions.condition.entityconditions.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entityconditions.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPowered extends EntityCondition<Boolean> {

    public IfPowered() {
        super(ConditionType.BOOLEAN, "ifPowered", "If powered", new String[]{}, false, " &cThe entity must being powered to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getCondition() && entity instanceof Creeper && !((Creeper)entity).isPowered()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }

        return true;
    }
}
