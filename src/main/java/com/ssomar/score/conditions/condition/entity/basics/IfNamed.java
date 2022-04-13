package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Nameable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfNamed extends EntityCondition<Boolean, String> {

    public IfNamed() {
        super(ConditionType.BOOLEAN, "ifNamed", "If named", new String[]{}, false, " &cThe entity must be named to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp()) && entity instanceof Nameable && ((Nameable)entity).getCustomName() == null) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }

        return true;
    }
}
