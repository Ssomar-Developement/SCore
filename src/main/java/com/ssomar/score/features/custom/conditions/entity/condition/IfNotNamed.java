package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.entity.Entity;

public class IfNotNamed extends EntityConditionFeature<BooleanFeature, IfNotNamed> {


    public IfNotNamed(FeatureParentInterface parent) {
        super(parent, "ifNotNamed", "If not named", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (hasCondition() && entity instanceof Nameable && ((Nameable) entity).getCustomName() != null) {
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
        setCondition(new BooleanFeature(getParent(), "ifNotNamed", false, "If not named", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNotNamed getNewInstance(FeatureParentInterface parent) {
        return new IfNotNamed(parent);
    }
}
