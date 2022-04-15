package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class IfGlowing extends EntityCondition<Boolean, String> {

    public IfGlowing() {
        super(ConditionType.BOOLEAN, "ifGlowing", "If glowing", new String[]{}, false, " &cThe entity must glowing to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp())) {
            boolean hasError= !entity.isGlowing();
            LivingEntity lE = (LivingEntity)entity;
            try {
                hasError = !lE.hasPotionEffect(PotionEffectType.GLOWING);
            }catch(Exception ignored) {}
            if(hasError) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
