package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.group.HasExecutableItemGroupFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfHasExecutableItems extends PlayerConditionFeature<HasExecutableItemGroupFeature, IfHasExecutableItems> {

    public IfHasExecutableItems(FeatureParentInterface parent) {
        super(parent, "ifHasExecutableItems", "If has Executableitems", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if (hasCondition() && !getCondition().verifHas(player.getInventory().getContents(), player.getInventory().getHeldItemSlot())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfHasExecutableItems getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new HasExecutableItemGroupFeature(getParent(), "ifHasExecutableItems", "If has Executableitems", new String[]{}, Material.DIAMOND, false, true));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getHasExecutableItems().isEmpty();
    }

    @Override
    public IfHasExecutableItems getNewInstance(FeatureParentInterface parent) {
        return new IfHasExecutableItems(parent);
    }
}
