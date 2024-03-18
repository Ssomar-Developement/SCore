package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;

public class IfAdult extends EntityConditionFeature<BooleanFeature, IfAdult> {

    public IfAdult(FeatureParentInterface parent) {
        super(parent, "ifAdult", "If adult", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifAdult", false, "If adult", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfAdult getNewInstance(FeatureParentInterface parent) {
        return new IfAdult(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && entity instanceof Ageable && !((Ageable) entity).isAdult()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfAdult getValue() {
        return this;
    }
}
