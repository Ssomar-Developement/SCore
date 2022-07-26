package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfNotSprinting extends PlayerConditionFeature<BooleanFeature, IfNotSprinting> {

    public IfNotSprinting(FeatureParentInterface parent) {
        super(parent, "ifNotSprinting", "If not sprinting", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && player.isSprinting()) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfNotSprinting getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNotSprinting", false, "If not sprinting", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNotSprinting getNewInstance(FeatureParentInterface parent) {
        return new IfNotSprinting(parent);
    }
}
