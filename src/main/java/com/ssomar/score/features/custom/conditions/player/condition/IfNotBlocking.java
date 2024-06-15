package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfNotBlocking extends PlayerConditionFeature<BooleanFeature, IfNotBlocking> {

    public IfNotBlocking(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifNotBlocking);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && player.isBlocking()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfNotBlocking getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifNotBlocking, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotBlocking getNewInstance(FeatureParentInterface parent) {
        return new IfNotBlocking(parent);
    }
}
