package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfBaby extends EntityCondition<Boolean, String> {

    public IfBaby() {
        super(ConditionType.BOOLEAN, "ifBaby", "If baby", new String[]{}, false, " &cThe entity must being baby to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp()) && entity instanceof Ageable && ((Ageable)entity).isAdult()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }

        return true;
    }
}
