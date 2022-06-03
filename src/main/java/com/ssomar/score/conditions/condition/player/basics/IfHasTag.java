package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfHasTag extends PlayerCondition<List<String>, List<String>> {


    public IfHasTag() {
        super(ConditionType.LIST_STRING, "ifHasTag", "If has tag", new String[]{}, new ArrayList<>(), " &cYou haven't the good tags to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            boolean notValid = false;
            for (String tag : getAllCondition(messageSender.getSp())) {
                if (!player.getScoreboardTags().contains(tag)) {
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
