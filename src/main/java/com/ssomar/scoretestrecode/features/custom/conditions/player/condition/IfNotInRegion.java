package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListRegionStringFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotInRegion extends PlayerConditionFeature<ListRegionStringFeature, IfNotInRegion> {

    public IfNotInRegion(FeatureParentInterface parent) {
        super(parent, "ifNotInRegion", "If not in region", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (SCore.hasWorldGuard) {
            if (hasCondition() && new WorldGuardAPI().isInRegion(player, getCondition().getValue())) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNotInRegion getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListRegionStringFeature(getParent(), "ifNotInRegion", new ArrayList<>(), "If not in region", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return  getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotInRegion getNewInstance() {
        return new IfNotInRegion(getParent());
    }
}
