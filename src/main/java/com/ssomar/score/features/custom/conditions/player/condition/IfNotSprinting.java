package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfNotSprinting extends PlayerConditionFeature<BooleanFeature, IfNotSprinting> {

    public IfNotSprinting(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotSprinting);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && player.isSprinting()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfNotSprinting getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNotSprinting, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotSprinting getNewInstance(FeatureParentInterface parent) {
        return new IfNotSprinting(parent);
    }
}
