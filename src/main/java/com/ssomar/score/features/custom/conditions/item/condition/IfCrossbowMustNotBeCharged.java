package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class IfCrossbowMustNotBeCharged extends ItemConditionFeature<BooleanFeature, IfCrossbowMustNotBeCharged> {

    public IfCrossbowMustNotBeCharged(FeatureParentInterface parent) {
        super(parent, "ifCrossbowMustNotBeCharged", "If crossbow must not be charged", new String[]{}, Material.CROSSBOW, false);
    }

    @Override
    public boolean verifCondition(ItemConditionRequest request) {

        ItemStack itemStack = request.getItemStack();
        if (hasCondition() && itemStack.getType().toString().contains("CROSSBOW")) {

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
        setCondition(new BooleanFeature(this, "ifCrossbowMustNotBeCharged", false, "If crossbow must not be charged", new String[]{}, Material.CROSSBOW, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfCrossbowMustNotBeCharged getNewInstance(FeatureParentInterface parent) {
        return new IfCrossbowMustNotBeCharged(parent);
    }
}
