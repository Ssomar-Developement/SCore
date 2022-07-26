package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.list.ListEntityTypeFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfPlayerNotMounts extends PlayerConditionFeature<ListEntityTypeFeature, IfPlayerNotMounts> {

    public IfPlayerNotMounts(FeatureParentInterface parent) {
        super(parent, "ifPlayerNotMounts", "If player not mounts", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Entity vehicle;
            boolean error = false;
            if ((vehicle = player.getVehicle()) != null) {
                if (getCondition().getValue().contains(vehicle.getType())) error = true;
            }

            if (error) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerNotMounts getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEntityTypeFeature(this, "ifPlayerNotMounts", new ArrayList<>(), "If player not mounts", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerNotMounts getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerNotMounts(parent);
    }
}
