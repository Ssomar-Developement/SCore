package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

public class IfTamed extends EntityConditionFeature<BooleanFeature, IfTamed> {

    public IfTamed(FeatureParentInterface parent) {
        super(parent, "ifTamed", "If tamed", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (hasCondition() && entity instanceof Tameable && !((Tameable) entity).isTamed()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfTamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifTamed", false, "If tamed", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfTamed getNewInstance(FeatureParentInterface parent) {
        return new IfTamed(parent);
    }
}
