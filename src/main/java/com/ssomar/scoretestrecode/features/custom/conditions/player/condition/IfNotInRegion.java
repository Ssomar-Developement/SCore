package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotInRegion extends PlayerCondition<List<String>, List<String>> {


    public IfNotInRegion() {
        super(ConditionType.LIST_REGION, "ifNotInRegion", "If not in region", new String[]{}, new ArrayList<>(), " &cYou aren't in the good region to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (SCore.hasWorldGuard) {
            if (isDefined() && new WorldGuardAPI().isInRegion(player, getAllCondition(messageSender.getSp()))) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
