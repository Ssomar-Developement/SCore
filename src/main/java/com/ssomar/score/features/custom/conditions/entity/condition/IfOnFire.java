package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;

public class IfOnFire extends EntityConditionFeature<BooleanFeature, IfOnFire> {

    public IfOnFire(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifOnFire);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && entity.getFireTicks() <= 0 ) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfOnFire getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifOnFire, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfOnFire getNewInstance(FeatureParentInterface parent) {
        return new IfOnFire(getParent());
    }
}
