package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

import java.util.Optional;

public class IfTamed extends EntityCondition<Boolean, String> {

    public IfTamed() {
        super(ConditionType.BOOLEAN, "ifTamed", "If tamed", new String[]{}, false, " &cThe entity must be tamed to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp()) && entity instanceof Tameable && !((Tameable)entity).isTamed()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }

        return true;
    }
}
