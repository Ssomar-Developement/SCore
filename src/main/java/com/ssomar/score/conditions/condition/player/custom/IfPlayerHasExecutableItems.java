package com.ssomar.score.conditions.condition.player.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfPlayerHasExecutableItems extends PlayerCondition<List<IfPlayerHasExecutableItem>, List<String>> {


    public IfPlayerHasExecutableItems() {
        super(ConditionType.CUSTOM_HAS_EXECUTABLE_ITEM, "ifPlayerHasExecutableItem", "If player has EI", new String[]{}, new ArrayList<>(), " &cYou don't have all correct ExecutableItems to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            if (SCore.hasExecutableItems) {
                for (IfPlayerHasExecutableItem cdt : getCondition()) {
                    if (cdt.isValid() && !cdt.verify(player)) {
                        sendErrorMsg(playerOpt, messageSender);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void removeCondition(String id) {
        IfPlayerHasExecutableItem toRemove = null;
        for(IfPlayerHasExecutableItem bAC : getCondition()) {
            if(bAC.getId().equals(id)) {
                toRemove = bAC;
                break;
            }
        }
        if(toRemove != null) {
            getCondition().remove(toRemove);
        }
    }

}
