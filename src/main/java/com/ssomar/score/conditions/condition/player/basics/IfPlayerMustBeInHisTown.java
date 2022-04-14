package com.ssomar.score.conditions.condition.player.basics;

import com.palmergames.bukkit.towny.TownyAPI;
import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.usedapi.GriefDefenderAPI;
import com.ssomar.score.usedapi.GriefPreventionAPI;
import com.ssomar.score.usedapi.LandsIntegrationAPI;
import com.ssomar.score.usedapi.TownyToolAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerMustBeInHisTown extends PlayerCondition<Boolean, String> {


    public IfPlayerMustBeInHisTown() {
        super(ConditionType.BOOLEAN, "IfPlayerMustBeInHisTown", "If player must be in his town", new String[]{}, false, " &cTo active this activator/item, you must be in your town !");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getAllCondition(messageSender.getSp())) {
            if (SCore.hasTowny) {
                if (!TownyToolAPI.playerIsInHisTown(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }
}
