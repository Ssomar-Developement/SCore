package com.ssomar.score.conditions.condition.playercondition.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfPlayerHasExecutableItems extends PlayerCondition<List<IfPlayerHasExecutableItem>> {


    public IfPlayerHasExecutableItems() {
        super(ConditionType.CUSTOM_HAS_EXECUTABLE_ITEM, "ifPlayerHasExecutableItem", "If player has EI", new String[]{}, new ArrayList<>(), " &cYou don't have all correct ExecutableItems to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().size() > 0) {
            if (SCore.hasExecutableItems) {
                for (IfPlayerHasExecutableItem cdt : getCondition()) {
                    if (!cdt.verify(player)) {
                        sendErrorMsg(playerOpt, messageSender);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
