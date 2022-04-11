package com.ssomar.score.conditions.condition.entityconditions.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entityconditions.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfName extends EntityCondition<List<String>> {

    public IfName() {
        super(ConditionType.LIST_STRING, "ifName", "If name", new String[]{}, new ArrayList<>(), " &cThe entity doesn't have a valid name to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getCondition() != null && !getCondition().isEmpty()) {
            boolean notValid = true;
            for(String name: getCondition()) {
                if(StringConverter.decoloredString(entity.getName()).equalsIgnoreCase(name)) {
                    notValid = false;
                    break;
                }
            }
            if(notValid) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }

        return true;
    }
}
