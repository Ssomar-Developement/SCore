package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfPlayerNotMounts extends PlayerCondition<List<EntityType>, List<String>> {


    public IfPlayerNotMounts() {
        super(ConditionType.LIST_ENTITYTYPE, "ifPlayerNotMounts", "If player not mounts", new String[]{}, new ArrayList<>(), " &cYou must not mount on a specific entity to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            Entity vehicle;
            boolean error = false;
            if ((vehicle = player.getVehicle()) != null) {
                if(getAllCondition(messageSender.getSp()).contains(vehicle.getType())) error = true;
            }

            if(error) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
