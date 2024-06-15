package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

public class IfNotTamed extends EntityConditionFeature<BooleanFeature, IfNotTamed> {

    public IfNotTamed(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotTamed);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp())) {
            if(!(entity instanceof Tameable)) return true;
            else if((entity instanceof Tameable && ((Tameable) entity).isTamed())) {
                runInvalidCondition(request);
                return false;
            }
        }

        return true;
    }

    @Override
    public IfNotTamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNotTamed, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotTamed getNewInstance(FeatureParentInterface parent) {
        return new IfNotTamed(parent);
    }
}
