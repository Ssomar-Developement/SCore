package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.condition.worldcondition.WorldCondition;
import com.ssomar.score.conditions.WorldConditions;
import com.ssomar.score.conditions.condition.worldcondition.basics.IfWeather;
import com.ssomar.score.conditions.condition.worldcondition.basics.IfWorldTime;

public class WorldConditionsManager extends ConditionsManager<WorldConditions, WorldCondition> {

    private static WorldConditionsManager instance;


    public WorldConditionsManager() {
        super(new WorldConditions());
        add(new IfWeather());
        add(new IfWorldTime());

    }

    public static WorldConditionsManager getInstance() {
        if (instance == null) instance = new WorldConditionsManager();
        return instance;
    }
}
