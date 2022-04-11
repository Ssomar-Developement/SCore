package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.condition.blockcondition.BlockConditions;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.basics.*;

public class BlockConditionsManager extends ConditionsManager<BlockConditions, BlockCondition> {

    private static BlockConditionsManager instance;

    public BlockConditionsManager() {
        super(new BlockConditions());
        add(new IfPlantFullyGrown());
        add(new IfIsPowered());
        add(new IfMustBeNotPowered());
        add(new IfMustBeNatural());
        add(new IfPlayerMustBeOnTheBlock());
        add(new IfNoPlayerMustBeOnTheBlock());

        add(new IfBlockAge());
        add(new IfBlockLocationX());
        add(new IfBlockLocationY());
        add(new IfBlockLocationZ());
        add(new IfUsage());

    }

    public static BlockConditionsManager getInstance() {
        if (instance == null) instance = new BlockConditionsManager();
        return instance;
    }
}
