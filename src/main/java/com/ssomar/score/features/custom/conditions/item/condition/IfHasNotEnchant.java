package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.list.ListEnchantAndLevelFeature;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class IfHasNotEnchant extends ItemConditionFeature<ListEnchantAndLevelFeature, IfHasNotEnchant> {

    public IfHasNotEnchant(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifHasNotEnchant);
    }

    @Override
    public boolean verifCondition(ItemConditionRequest request) {

        if (hasCondition()) {
            ItemStack itemStack = request.getItemStack();
            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if (hasItemMeta) itemMeta = itemStack.getItemMeta();

            if (!hasItemMeta) {
                runInvalidCondition(request);
                return false;
            }
            Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
            Map<Enchantment, Integer> condition = getCondition().getValue();
            for (Enchantment enchant : condition.keySet()) {
                if (enchants.containsKey(enchant) && condition.get(enchant).equals(enchants.get(enchant))) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfHasNotEnchant getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEnchantAndLevelFeature(this, new HashMap<>(), FeatureSettingsSCore.ifHasNotEnchant, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfHasNotEnchant getNewInstance(FeatureParentInterface parent) {
        return new IfHasNotEnchant(parent);
    }
}
