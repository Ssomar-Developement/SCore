package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class IfCrossbowMustNotBeCharged extends ItemConditionFeature<BooleanFeature, IfCrossbowMustNotBeCharged> {

    public IfCrossbowMustNotBeCharged(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifCrossbowMustNotBeCharged);
    }

    @Override
    public boolean verifCondition(ItemConditionRequest request) {

        ItemStack itemStack = request.getItemStack();
        if (getCondition().getValue(request.getSp()) && itemStack.getType().toString().contains("CROSSBOW")) {

            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if (hasItemMeta) itemMeta = itemStack.getItemMeta();

            if (hasItemMeta && itemMeta instanceof CrossbowMeta) {
                CrossbowMeta cMeta = (CrossbowMeta) itemMeta;
                boolean charged = cMeta.hasChargedProjectiles();

                if (charged) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfCrossbowMustNotBeCharged getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, false, FeatureSettingsSCore.ifCrossbowMustNotBeCharged, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfCrossbowMustNotBeCharged getNewInstance(FeatureParentInterface parent) {
        return new IfCrossbowMustNotBeCharged(parent);
    }
}
