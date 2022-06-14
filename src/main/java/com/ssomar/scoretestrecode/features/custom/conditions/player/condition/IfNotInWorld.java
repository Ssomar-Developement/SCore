package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotInWorld extends PlayerCondition<List<String>, List<String>> {


    public IfNotInWorld() {
        super(ConditionType.LIST_WORLD, "ifNotInWorld", "If not in world", new String[]{}, new ArrayList<>(), " &cYou aren't in the good world to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            boolean notValid = false;
            for (String s : getAllCondition(messageSender.getSp())) {
                if (player.getWorld().getName().equalsIgnoreCase(s)) {
                    notValid = true;
                    break;
                }
            }
            if (notValid) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
