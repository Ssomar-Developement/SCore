package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Nameable;
import org.bukkit.entity.Entity;

public class IfNotNamed extends EntityConditionFeature<BooleanFeature, IfNotNamed> {


    public IfNotNamed(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotNamed);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && entity instanceof Nameable && ((Nameable) entity).getCustomName() != null) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfNotNamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNotNamed, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotNamed getNewInstance(FeatureParentInterface parent) {
        return new IfNotNamed(parent);
    }
}
