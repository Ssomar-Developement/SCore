package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotInRegion extends PlayerCondition<List<String>> {


    public IfNotInRegion() {
        super(ConditionType.LIST_REGION, "ifNotInRegion", "If not in region", new String[]{}, new ArrayList<>(), " &cYou aren't in the good region to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (SCore.hasWorldGuard) {
            if (getCondition() != null && !getCondition().isEmpty() && new WorldGuardAPI().isInRegion(player, getCondition())) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
