package com.ssomar.score.conditions.managers;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.entity.EntityConditions;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.conditions.condition.entity.basics.*;

public class EntityConditionsManager extends ConditionsManager<EntityConditions, EntityCondition> {

    private static EntityConditionsManager instance;

    public EntityConditionsManager() {
        super(new EntityConditions());
        add(new IfAdult());
        add(new IfBaby());
        add(new IfGlowing());
        add(new IfInvulnerable());
        add(new IfOnFire());
        add(new IfPowered());
        add(new IfTamed());
        if(!SCore.is1v11Less())
            add(new IfNamed());
        add(new IfFrozen());

        add(new IfEntityHealth());

        add(new IfName());
        add(new IfNotEntityType());

    }

    public static EntityConditionsManager getInstance() {
        if (instance == null) instance = new EntityConditionsManager();
        return instance;
    }
}
