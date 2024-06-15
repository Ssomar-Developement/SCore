package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Nameable;
import org.bukkit.entity.Entity;

public class IfNamed extends EntityConditionFeature<BooleanFeature, IfNamed> {


    public IfNamed(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNamed);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && entity instanceof Nameable && ((Nameable) entity).getCustomName() == null) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfNamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNamed, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNamed getNewInstance(FeatureParentInterface parent) {
        return new IfNamed(parent);
    }
}
