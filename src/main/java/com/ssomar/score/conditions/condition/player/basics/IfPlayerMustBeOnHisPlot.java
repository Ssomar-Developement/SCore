package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.usedapi.PlotSquaredAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerMustBeOnHisPlot extends PlayerCondition<Boolean, String> {


    public IfPlayerMustBeOnHisPlot() {
        super(ConditionType.BOOLEAN, "ifPlayerMustBeOnHisPlot", "If player must be on his plot", new String[]{}, false, " &cTo active this activator/item, you must be on your Plot or friend plot !");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if(SCore.hasPlotSquared) {
            if(getAllCondition(messageSender.getSp())) {
                if(!PlotSquaredAPI.playerIsInHisPlot(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }
}
