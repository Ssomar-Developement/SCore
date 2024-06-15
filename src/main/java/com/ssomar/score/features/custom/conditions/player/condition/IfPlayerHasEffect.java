package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListEffectAndLevelFeature;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class IfPlayerHasEffect extends PlayerConditionFeature<ListEffectAndLevelFeature, IfPlayerHasEffect> {

    public IfPlayerHasEffect(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerHasEffect);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Map<PotionEffectType, Integer> condition = getCondition().getValue();
            for (PotionEffectType pET : condition.keySet()) {
                if (!player.hasPotionEffect(pET)) {
                    runInvalidCondition(request);
                    return false;
                } else {
                    if (player.getPotionEffect(pET).getAmplifier() < condition.get(pET)) {
                        runInvalidCondition(request);
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
        setCondition(new ListEffectAndLevelFeature(this,  new HashMap<>(), FeatureSettingsSCore.ifPlayerHasEffect, true));
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
