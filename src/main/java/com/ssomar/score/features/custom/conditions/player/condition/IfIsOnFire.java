package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfIsOnFire extends PlayerConditionFeature<BooleanFeature, IfIsOnFire> {

    public IfIsOnFire(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsOnFire);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && player.getFireTicks() <= 0) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfIsOnFire getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifIsOnFire, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfIsOnFire getNewInstance(FeatureParentInterface parent) {
        return new IfIsOnFire(parent);
    }
}
