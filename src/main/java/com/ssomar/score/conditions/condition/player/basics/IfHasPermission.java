package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfHasPermission extends PlayerCondition<List<String>, List<String>> {


    public IfHasPermission() {
        super(ConditionType.LIST_PERMISSION, "ifHasPermission", "If has permission", new String[]{}, new ArrayList<>(), " &cYou doesn't have the permission to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            boolean valid = true;
            for (String perm : getAllCondition(messageSender.getSp())) {
                if (!player.hasPermission(perm)) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
