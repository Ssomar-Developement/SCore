package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;

public class IfFromSpawner extends EntityConditionFeature<BooleanFeature, IfFromSpawner> {

    public IfFromSpawner(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifFromSpawner);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifFromSpawner, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfFromSpawner getNewInstance(FeatureParentInterface parent) {
        return new IfFromSpawner(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && !entity.hasMetadata("fromSpawner")) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfFromSpawner getValue() {
        return this;
    }
}
