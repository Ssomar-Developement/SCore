package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;

public class IfBaby extends EntityConditionFeature<BooleanFeature, IfBaby> {

    public IfBaby(FeatureParentInterface parent) {
        super(parent, "ifBaby", "If baby", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (hasCondition() && entity instanceof Ageable && ((Ageable) entity).isAdult()) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfBaby getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifBaby", false, "If baby", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfBaby getNewInstance(FeatureParentInterface parent) {
        return new IfBaby(parent);
    }
}
