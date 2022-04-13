package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfEntityHealth extends EntityCondition<String, String> {

    public IfEntityHealth() {
        super(ConditionType.NUMBER_CONDITION, "ifEntityHealth", "If entity health", new String[]{}, "", " &cThe health of the entity is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(isDefined() && entity instanceof LivingEntity) {
            LivingEntity lE = (LivingEntity) entity;
            if(!StringCalculation.calculation(getAllCondition(messageSender.getSp()), lE.getHealth())) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }

        return true;
    }
}
