package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfIsNotOnFire extends PlayerConditionFeature<BooleanFeature, IfIsNotOnFire> {

    public IfIsNotOnFire(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsNotOnFire);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && player.getFireTicks() > 0) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfIsNotOnFire getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifIsNotOnFire, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfIsNotOnFire getNewInstance(FeatureParentInterface parent) {
        return new IfIsNotOnFire(parent);
    }
}
