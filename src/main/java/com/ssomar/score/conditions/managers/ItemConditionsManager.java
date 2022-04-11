package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.condition.entityconditions.EntityCondition;
import com.ssomar.score.conditions.condition.entityconditions.EntityConditions;
import com.ssomar.score.conditions.condition.item.ItemCondition;
import com.ssomar.score.conditions.condition.item.ItemConditions;
import com.ssomar.score.conditions.condition.item.basics.*;

public class ItemConditionsManager extends ConditionsManager<ItemConditions, ItemCondition> {

    private static ItemConditionsManager instance;

    public ItemConditionsManager() {
        super(new ItemConditions());
        add(new IfCrossbowMustBeCharged());
        add(new IfCrossbowMustNotBeCharged());

        add(new IfDurability());
        add(new IfUsage());

        add(new IfHasEnchant());
        add(new IfHasNotEnchant());

    }

    public static ItemConditionsManager getInstance() {
        if (instance == null) instance = new ItemConditionsManager();
        return instance;
    }
}
