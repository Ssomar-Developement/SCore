package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IfPlayerHasEffect extends PlayerCondition<Map<PotionEffectType, Integer>, Map<String, String>> {


    public IfPlayerHasEffect() {
        super(ConditionType.MAP_EFFECT_AMOUNT, "ifPlayerHasEffect", "If player has effect", new String[]{}, new HashMap<>(), " &cYou don't have all correct effects to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            Map<PotionEffectType, Integer> condition = getAllCondition(messageSender.getSp());
            for (PotionEffectType pET : condition.keySet()) {
                if (!player.hasPotionEffect(pET)) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                } else {
                    if (player.getPotionEffect(pET).getAmplifier() < condition.get(pET)) {
                        sendErrorMsg(playerOpt, messageSender);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
