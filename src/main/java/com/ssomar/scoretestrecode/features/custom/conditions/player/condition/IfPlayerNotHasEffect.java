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

public class IfPlayerNotHasEffect extends PlayerConditionFeature<ListEffectAndLevelFeature, IfPlayerNotHasEffect> {

    public IfPlayerNotHasEffect(FeatureParentInterface parent) {
        super(parent, "ifPlayerNotHasEffect", "If player not has effect", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Map<PotionEffectType, Integer> condition = getCondition().getValue();
            for (PotionEffectType pET : condition.keySet()) {
                if (player.hasPotionEffect(pET)) {
                    if (player.getPotionEffect(pET).getAmplifier() >= condition.get(pET)) {
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
    public IfPlayerNotHasEffect getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEffectAndLevelFeature(this, "ifPlayerNotHasEffect", new HashMap<>(), "If player not has effect", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerNotHasEffect getNewInstance() {
        return new IfPlayerNotHasEffect(getParent());
    }
}
