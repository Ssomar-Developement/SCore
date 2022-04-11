package com.ssomar.score.conditions.managers;

import com.ssomar.score.conditions.BlockConditions;
import com.ssomar.score.conditions.PlayerConditions;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.basics.*;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.conditions.condition.playercondition.basics.*;

public class PlayerConditionsManager extends ConditionsManager<PlayerConditions, PlayerCondition> {

    private static PlayerConditionsManager instance;

    public PlayerConditionsManager() {
        super(new PlayerConditions());
        add(new IfSneaking());
        add(new IfNotSneaking());
        add(new IfBlocking());
        add(new IfNotBlocking());
        add(new IfFlying());
        add(new IfNotFlying());
        add(new IfGliding());
        add(new IfNotGliding());
        add(new IfSprinting());
        add(new IfNotSprinting());
        add(new IfSwimming());
        add(new IfNotSwimming());
        add(new IfIsInTheAir());
        add(new IfIsNotInTheAir());

        add(new IfIsOnTheBlock());
        add(new IfIsNotOnTheBlock());

        add(new IfLightLevel());
        add(new IfPlayerHealth());
        add(new IfPlayerFoodLevel());
        add(new IfPlayerEXP());
        add(new IfPlayerLevel());
        add(new IfPosX());
        add(new IfPosY());
        add(new IfPosZ());

        add(new IfHasPermission());
        add(new IfNotHasPermission());
        add(new IfInBiome());
        add(new IfNotInBiome());
        add(new IfInRegion());
        add(new IfNotInRegion());
        add(new IfInWorld());
        add(new IfNotInWorld());
        add(new IfTargetBlock());
        add(new IfNotTargetBlock());

        sortCorrectly();
    }

    public static PlayerConditionsManager getInstance() {
        if (instance == null) instance = new PlayerConditionsManager();
        return instance;
    }
}
