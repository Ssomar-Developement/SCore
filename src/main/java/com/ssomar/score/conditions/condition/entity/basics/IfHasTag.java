package com.ssomar.score.conditions.condition.entity.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfHasTag extends EntityCondition<List<String>, List<String>> {


    public IfHasTag() {
        super(ConditionType.LIST_STRING, "ifHasTag", "If has tag", new String[]{}, new ArrayList<>(), " &cThe entity doesn't have the good tags to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            boolean notValid = false;
            for (String tag : getAllCondition(messageSender.getSp())) {
                if (!entity.getScoreboardTags().contains(tag)) {
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
