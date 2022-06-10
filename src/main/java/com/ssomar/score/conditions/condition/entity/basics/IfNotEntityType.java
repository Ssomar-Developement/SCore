package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotEntityType extends EntityCondition<List<EntityType>, List<String>> {

    public IfNotEntityType() {
        super(ConditionType.LIST_ENTITYTYPE, "ifNotEntityType", "If not entityType", new String[]{}, new ArrayList<>(), " &cThe entity hasn't the good type to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(isDefined()) {
            for(EntityType et: getAllCondition(messageSender.getSp())) {
                if(entity.getType().equals(et)) return false;
            }
        }

        return true;
    }
}