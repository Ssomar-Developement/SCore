package com.ssomar.score.conditions.condition.worldcondition;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class WorldCondition<T> extends Condition<T> {

    public WorldCondition(ConditionType conditionType, String configName, String editorName, String [] editorDescription, T condition, String defaultErrorMsg) {
        super(conditionType, configName, editorName, editorDescription, condition, defaultErrorMsg);
    }

    public abstract boolean verifCondition(World world, Optional<Player> playerOpt, SendMessage messageSender);
}
