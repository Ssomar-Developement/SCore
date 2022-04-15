package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotHasPermission extends PlayerCondition<List<String>, List<String>> {


    public IfNotHasPermission() {
        super(ConditionType.LIST_PERMISSION, "ifNotHasPermission", "If has not permission", new String[]{}, new ArrayList<>(), " &cYou have a permission that shouldnt have to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            for (String perm : getAllCondition(messageSender.getSp())) {
                if (player.hasPermission(perm)) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }
}
