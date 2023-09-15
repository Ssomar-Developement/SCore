package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.custom.entities.group.EntityTypeGroupFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class IfNotEntityType extends EntityConditionFeature<EntityTypeGroupFeature, IfNotEntityType> {

    public IfNotEntityType(FeatureParentInterface parent) {
        super(parent, "ifNotEntityType", "If not entityType", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            for (EntityType et : getCondition().getValue().getEntityTypeList()) {
                if (entity.getType().equals(et)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfNotEntityType getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new EntityTypeGroupFeature(this, "ifNotEntityType", "If not entityType", new String[]{}, false, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getEntityTypes().size() > 0;
    }

    @Override
    public IfNotEntityType getNewInstance(FeatureParentInterface parent) {
        return new IfNotEntityType(parent);
    }
}
