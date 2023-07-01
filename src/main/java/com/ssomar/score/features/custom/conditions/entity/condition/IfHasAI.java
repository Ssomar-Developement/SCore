package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IfHasAI extends EntityConditionFeature<BooleanFeature, IfHasAI> {

    public IfHasAI(FeatureParentInterface parent) {
        super(parent, "ifHasAI", "If has AI", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifHasAI", false, "If has AI", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfHasAI getNewInstance(FeatureParentInterface parent) {
        return new IfHasAI(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (hasCondition() && entity instanceof LivingEntity && !((LivingEntity) entity).hasAI()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfHasAI getValue() {
        return this;
    }
}
