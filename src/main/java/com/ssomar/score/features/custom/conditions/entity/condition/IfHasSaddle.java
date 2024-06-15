package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.InventoryHolder;

public class IfHasSaddle extends EntityConditionFeature<BooleanFeature, IfHasSaddle> {

    public IfHasSaddle(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifHasSaddle);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifHasSaddle, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfHasSaddle getNewInstance(FeatureParentInterface parent) {
        return new IfHasSaddle(parent);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        Entity entity = request.getEntity();
        if (getCondition().getValue(request.getSp()) && (!(entity instanceof InventoryHolder) || !(((InventoryHolder) entity).getInventory() instanceof AbstractHorseInventory) || ((AbstractHorseInventory) ((InventoryHolder) entity).getInventory()).getSaddle() == null)) {
            runInvalidCondition(request);
            return false;
        }

        return true;
    }

    @Override
    public IfHasSaddle getValue() {
        return this;
    }
}
