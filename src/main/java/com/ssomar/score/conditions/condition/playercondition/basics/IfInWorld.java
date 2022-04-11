package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfInWorld extends PlayerCondition<List<String>> {


    public IfInWorld() {
        super(ConditionType.LIST_WORLD, "ifInWorld", "If in world", new String[]{}, new ArrayList<>(), " &cYou aren't in the good world to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().size() > 0) {
            boolean notValid = true;
            for (String s : getCondition()) {
                if (player.getWorld().getName().equalsIgnoreCase(s)) {
                    notValid = false;
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
