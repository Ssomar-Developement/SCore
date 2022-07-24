package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.list.ListWorldFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfNotInWorld extends PlayerConditionFeature<ListWorldFeature, IfNotInWorld> {

    public IfNotInWorld(FeatureParentInterface parent) {
        super(parent, "ifNotInWorld", "If not in world", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            boolean notValid = false;
            for (String s : getCondition().getValue()) {
                if (player.getWorld().getName().equalsIgnoreCase(s)) {
                    notValid = true;
                    break;
                }
            }
            if (notValid) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNotInWorld getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListWorldFeature(getParent(), "ifNotInWorld", new ArrayList<>(), "If not in world", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotInWorld getNewInstance(FeatureParentInterface parent) {
        return new IfNotInWorld(parent);
    }
}
