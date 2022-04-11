package com.ssomar.score.conditions.condition.entityconditions.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entityconditions.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotEntityType extends EntityCondition<List<EntityType>> {

    public IfNotEntityType() {
        super(ConditionType.LIST_ENTITYTYPE, "ifNotEntityType", "If not entityType", new String[]{}, new ArrayList<>(), " &cThe entity hasn't the good type to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getCondition() != null && getCondition().size() > 0) {
            for(EntityType et: getCondition()) {
                if(entity.getType().equals(et)) return false;
            }
        }

        return true;
    }
}
