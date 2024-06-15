package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.commands.runnable.player.events.StunEvent;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfStunned extends PlayerConditionFeature<BooleanFeature, IfStunned> {


    public IfStunned(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifStunned);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && !StunEvent.stunPlayers.containsKey(player.getUniqueId())) {
            runInvalidCondition(request);
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
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifStunned, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfStunned getNewInstance(FeatureParentInterface parent) {
        return new IfStunned(parent);
    }
}
