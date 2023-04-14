package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.list.ListEffectAndLevelFeature;
import com.ssomar.score.utils.messages.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IfPlayerHasEffect extends PlayerConditionFeature<ListEffectAndLevelFeature, IfPlayerHasEffect> {

    public IfPlayerHasEffect(FeatureParentInterface parent) {
        super(parent, "ifPlayerHasEffect", "If player has effect", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Map<PotionEffectType, Integer> condition = getCondition().getValue();
            for (PotionEffectType pET : condition.keySet()) {
                if (!player.hasPotionEffect(pET)) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                } else {
                    if (player.getPotionEffect(pET).getAmplifier() < condition.get(pET)) {
                        sendErrorMsg(playerOpt, messageSender);
                        cancelEvent(event);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerHasEffect getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEffectAndLevelFeature(this, "ifPlayerHasEffect", new HashMap<>(), "If player has effect", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerHasEffect getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerHasEffect(parent);
    }
}
