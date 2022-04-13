package com.ssomar.score.conditions.condition.customei;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public abstract class CustomEICondition<T, Y> extends Condition<T, Y> {

    public CustomEICondition(ConditionType conditionType, String configName, String editorName, String [] editorDescription, T condition, String defaultErrorMsg) {
        super(conditionType, configName, editorName, editorDescription, condition, defaultErrorMsg);
    }

    public abstract boolean verifCondition(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender);
}
