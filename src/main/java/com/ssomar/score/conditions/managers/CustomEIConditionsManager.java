package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.BlockConditions;
import com.ssomar.score.conditions.condition.blockcondition.basics.*;
import com.ssomar.score.conditions.condition.customei.CustomEICondition;
import com.ssomar.score.conditions.condition.customei.CustomEIConditions;
import com.ssomar.score.conditions.condition.customei.basics.IfNeedPlayerConfirmation;
import com.ssomar.score.conditions.condition.customei.basics.IfNotOwnerOfTheEI;
import com.ssomar.score.conditions.condition.customei.basics.IfOwnerOfTheEI;

public class CustomEIConditionsManager extends ConditionsManager<CustomEIConditions, CustomEICondition> {

    private static CustomEIConditionsManager instance;

    public CustomEIConditionsManager() {
        super(new CustomEIConditions());
        add(new IfNeedPlayerConfirmation());
        add(new IfOwnerOfTheEI());
        add(new IfNotOwnerOfTheEI());

    }

    public static CustomEIConditionsManager getInstance() {
        if (instance == null) instance = new CustomEIConditionsManager();
        return instance;
    }
}
