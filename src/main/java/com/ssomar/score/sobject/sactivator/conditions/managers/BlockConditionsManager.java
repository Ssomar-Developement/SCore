package com.ssomar.score.sobject.sactivator.conditions.managers;

import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.basics.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BlockConditionsManager extends ConditionsManager<BlockConditions, BlockCondition> {

    private static BlockConditionsManager instance;

    public BlockConditionsManager() {
        super(new BlockConditions());
        add(new IfPlantFullyGrown());
        add(new IfBlockAge());
        add(new IfBlockLocationX());
        add(new IfBlockLocationY());
        add(new IfBlockLocationZ());
        add(new IfIsPowered());
        add(new IfMustBeNatural());
        add(new IfMustBeNotPowered());
        add(new IfUsage());
        add(new IfPlayerMustBeOnTheBlock());
        add(new IfNoPlayerMustBeOnTheBlock());
    }

    public static BlockConditionsManager getInstance() {
        if (instance == null) instance = new BlockConditionsManager();
        return instance;
    }
}
