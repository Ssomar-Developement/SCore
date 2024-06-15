package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

public class IfTamed extends EntityConditionFeature<BooleanFeature, IfTamed> {

    public IfTamed(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifTamed);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp())) {
            if(!(entity instanceof Tameable) || (entity instanceof Tameable && !((Tameable) entity).isTamed())) {
                runInvalidCondition(request);
                return false;
            }
        }

        return true;
    }

    @Override
    public IfTamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifTamed, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfTamed getNewInstance(FeatureParentInterface parent) {
        return new IfTamed(parent);
    }
}
