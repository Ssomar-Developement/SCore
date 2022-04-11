package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.usedapi.IridiumSkyblockTool;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerMustBeOnHisIsland extends PlayerCondition<Boolean> {


    public IfPlayerMustBeOnHisIsland() {
        super(ConditionType.BOOLEAN, "ifPlayerMustBeOnHisIsland", "If player must be on his island", new String[]{}, false, " &cTo active this activator/item, you must be on your Island !");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if(SCore.hasIridiumSkyblock) {
            if(getCondition()) {
                if(!IridiumSkyblockTool.playerIsOnHisIsland(player)) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }
}
