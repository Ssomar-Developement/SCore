package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.condition.entityconditions.EntityConditions;
import com.ssomar.score.conditions.condition.entityconditions.EntityCondition;

public class EntityConditionsManager extends ConditionsManager<EntityConditions, EntityCondition> {

    private static EntityConditionsManager instance;

    public EntityConditionsManager() {
        super(new EntityConditions());

    }

    public static EntityConditionsManager getInstance() {
        if (instance == null) instance = new EntityConditionsManager();
        return instance;
    }
}
