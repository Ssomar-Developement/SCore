package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfPlayerIsRiding extends PlayerConditionFeature<BooleanFeature, IfPlayerIsRiding> {

    public IfPlayerIsRiding(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerIsRiding);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && !player.isInsideVehicle()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfPlayerIsRiding getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifPlayerIsRiding));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfPlayerIsRiding getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerIsRiding(parent);
    }
}
