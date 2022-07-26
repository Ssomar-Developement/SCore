package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.commands.runnable.player.events.StunEvent;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfStunned extends PlayerConditionFeature<BooleanFeature, IfStunned> {


    public IfStunned(FeatureParentInterface parent) {
        super(parent, "ifStunned", "If stunned", new String[]{"&7&oBy the custom player", "&7&ocommand &eSTUN_ENABLE"}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StunEvent.stunPlayers.containsKey(player.getUniqueId())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfStunned getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifStunned", false, "If stunned", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfStunned getNewInstance(FeatureParentInterface parent) {
        return new IfStunned(parent);
    }
}
