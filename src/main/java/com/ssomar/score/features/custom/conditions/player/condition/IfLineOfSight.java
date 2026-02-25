package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Player;

public class IfLineOfSight extends PlayerConditionFeature<BooleanFeature, IfLineOfSight> {

    public IfLineOfSight(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifLineOfSight);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp())) {
            boolean hasLineOfSight = player.getTargetEntity(50) != null;
            if (!hasLineOfSight) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfLineOfSight getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifLineOfSight));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfLineOfSight getNewInstance(FeatureParentInterface parent) {
        return new IfLineOfSight(parent);
    }
}
