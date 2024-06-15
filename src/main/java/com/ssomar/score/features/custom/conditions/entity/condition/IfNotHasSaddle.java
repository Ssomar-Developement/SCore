package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.InventoryHolder;

public class IfNotHasSaddle extends EntityConditionFeature<BooleanFeature, IfNotHasSaddle> {

    public IfNotHasSaddle(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotHasSaddle);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNotHasSaddle, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotHasSaddle getNewInstance(FeatureParentInterface parent) {
        return new IfNotHasSaddle(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && (entity instanceof InventoryHolder) && ((InventoryHolder) entity).getInventory() instanceof AbstractHorseInventory && ((AbstractHorseInventory) ((InventoryHolder) entity).getInventory()).getSaddle() != null) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfNotHasSaddle getValue() {
        return this;
    }
}
