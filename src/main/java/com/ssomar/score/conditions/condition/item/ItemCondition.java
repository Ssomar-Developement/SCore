package com.ssomar.score.conditions.condition.item;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public abstract class ItemCondition<T> extends Condition<T> {

    public ItemCondition(ConditionType conditionType, String configName, String editorName, String [] editorDescription, T condition, String defaultErrorMsg) {
        super(conditionType, configName, editorName, editorDescription, condition, defaultErrorMsg);
    }

    public abstract boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender);
}
