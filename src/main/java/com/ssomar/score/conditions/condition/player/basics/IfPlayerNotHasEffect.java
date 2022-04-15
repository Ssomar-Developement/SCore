package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IfPlayerNotHasEffect extends PlayerCondition<Map<PotionEffectType, Integer>, Map<String, String>> {


    public IfPlayerNotHasEffect() {
        super(ConditionType.MAP_EFFECT_AMOUNT, "ifPlayerNotHasEffect", "If player not has effect", new String[]{}, new HashMap<>(), " &cYou have an effect that you shouldn't have to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            Map<PotionEffectType, Integer> condition = getAllCondition(messageSender.getSp());
            for (PotionEffectType pET : condition.keySet()) {
                if (player.hasPotionEffect(pET)) {
                    if (player.getPotionEffect(pET).getAmplifier() >= condition.get(pET)) {
                        sendErrorMsg(playerOpt, messageSender);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
