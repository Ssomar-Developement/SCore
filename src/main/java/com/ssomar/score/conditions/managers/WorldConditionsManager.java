package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.condition.world.WorldCondition;
import com.ssomar.score.conditions.condition.world.WorldConditions;
import com.ssomar.score.conditions.condition.world.basics.IfWeather;
import com.ssomar.score.conditions.condition.world.basics.IfWorldTime;

public class WorldConditionsManager extends ConditionsManager<WorldConditions, WorldCondition> {

    private static WorldConditionsManager instance;


    public WorldConditionsManager() {
        super(new WorldConditions());
        add(new IfWorldTime());

        add(new IfWeather());

    }

    public static WorldConditionsManager getInstance() {
        if (instance == null) instance = new WorldConditionsManager();
        return instance;
    }
}
