package com.ssomar.score.conditions.condition.entityconditions;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class EntityCondition<T> extends Condition<T> {

    public EntityCondition(ConditionType conditionType, String configName, String editorName, String [] editorDescription, T condition, String defaultErrorMsg) {
        super(conditionType, configName, editorName, editorDescription, condition, defaultErrorMsg);
    }

    public abstract boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender);
}
