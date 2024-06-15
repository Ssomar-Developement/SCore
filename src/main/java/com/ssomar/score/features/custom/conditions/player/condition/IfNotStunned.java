package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.commands.runnable.player.events.StunEvent;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfNotStunned extends PlayerConditionFeature<BooleanFeature, IfNotStunned> {


    public IfNotStunned(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotStunned);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && StunEvent.stunPlayers.containsKey(player.getUniqueId())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfNotStunned getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNotStunned, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotStunned getNewInstance(FeatureParentInterface parent) {
        return new IfNotStunned(parent);
    }
}
