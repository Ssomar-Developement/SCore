package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfInBiome extends PlayerCondition<List<Biome>, List<String>> {


    public IfInBiome() {
        super(ConditionType.LIST_BIOME, "ifInBiome", "If in biome", new String[]{}, new ArrayList<>(), " &cYou aren't in the good biome to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            boolean notValid = true;
            for (Biome b : getAllCondition(messageSender.getSp())) {
                if (player.getLocation().getBlock().getBiome().equals(b)) {
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
