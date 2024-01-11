package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

public class IfFromSpawner extends EntityConditionFeature<BooleanFeature, IfFromSpawner> {

    public IfFromSpawner(FeatureParentInterface parent) {
        super(parent, "ifFromSpawner", "If from spawner", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifFromSpawner", false, "If from spawner", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfFromSpawner getNewInstance(FeatureParentInterface parent) {
        return new IfFromSpawner(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (hasCondition() && !entity.hasMetadata("fromSpawner")) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfFromSpawner getValue() {
        return this;
    }
}
