package com.ssomar.score.conditions.condition.blockcondition;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class BlockCondition<T> extends Condition<T> {

    public BlockCondition(ConditionType conditionType, String configName, String editorName, String [] editorDescription, T condition, String defaultErrorMsg) {
        super(conditionType, configName, editorName, editorDescription, condition, defaultErrorMsg);
    }

    public abstract boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender);
}
