package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.usedapi.GriefDefenderAPI;
import com.ssomar.score.usedapi.GriefPreventionAPI;
import com.ssomar.score.usedapi.LandsIntegrationAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerMustBeOnHisClaim extends PlayerCondition<Boolean, String> {


    public IfPlayerMustBeOnHisClaim() {
        super(ConditionType.BOOLEAN, "ifPlayerMustBeOnHisClaim", "If player must be on his claim", new String[]{}, false, " &cTo active this activator/item, you must be on your Claim or friend claim !");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getAllCondition(messageSender.getSp())) {
            if (SCore.hasLands) {
                LandsIntegrationAPI lands = new LandsIntegrationAPI(SCore.plugin);
                if (!lands.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
            if (SCore.hasGriefPrevention) {
                if (!GriefPreventionAPI.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }

            if (SCore.hasGriefDefender) {
                if (!GriefDefenderAPI.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }
}
