package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListEffectAndLevelFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IfPlayerHasEffectEquals extends PlayerConditionFeature<ListEffectAndLevelFeature, IfPlayerHasEffectEquals> {

    public IfPlayerHasEffectEquals(FeatureParentInterface parent) {
        super(parent, "ifPlayerHasEffectEquals", "If player has effect equals", new String[]{}, Material.ANVIL, false);
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
                    if (player.getPotionEffect(pET).getAmplifier() != condition.get(pET)) {
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
    public IfPlayerHasEffectEquals getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEffectAndLevelFeature(this, "ifPlayerHasEffectEquals", new HashMap<>(), "If player has effect equals", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerHasEffectEquals getNewInstance() {
        return new IfPlayerHasEffectEquals(getParent());
    }
}
