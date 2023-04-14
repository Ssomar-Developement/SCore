package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.list.ListEntityTypeFeature;
import com.ssomar.score.utils.messages.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfPlayerMounts extends PlayerConditionFeature<ListEntityTypeFeature, IfPlayerMounts> {

    public IfPlayerMounts(FeatureParentInterface parent) {
        super(parent, "ifPlayerMounts", "If player mounts", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Entity vehicle;
            boolean error = false;
            if ((vehicle = player.getVehicle()) != null) {
                if (!getCondition().getValue().contains(vehicle.getType())) error = true;
            } else error = true;

            if (error) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerMounts getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEntityTypeFeature(this, "ifPlayerMounts", new ArrayList<>(), "If player mounts", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerMounts getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMounts(parent);
    }
}
