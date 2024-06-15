package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;

public class IfBlocking extends PlayerConditionFeature<BooleanFeature, IfBlocking> {

    public IfBlocking(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifBlocking);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (getCondition().getValue(request.getSp()) && !request.getPlayer().isBlocking()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfBlocking getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifBlocking, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfBlocking getNewInstance(FeatureParentInterface parent) {
        return new IfBlocking(parent);
    }
}
