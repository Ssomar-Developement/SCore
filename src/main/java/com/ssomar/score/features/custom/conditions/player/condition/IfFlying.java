package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfFlying extends PlayerConditionFeature<BooleanFeature, IfFlying> {


    public IfFlying(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifFlying);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && !player.isFlying()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }


    @Override
    public IfFlying getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifFlying, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfFlying getNewInstance(FeatureParentInterface parent) {
        return new IfFlying(parent);
    }
}
