package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfSneaking extends PlayerConditionFeature<BooleanFeature, IfSneaking> {


    public IfSneaking(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifSneaking);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && !player.isSneaking()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfSneaking getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifSneaking, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfSneaking getNewInstance(FeatureParentInterface parent) {
        return new IfSneaking(parent);
    }
}
