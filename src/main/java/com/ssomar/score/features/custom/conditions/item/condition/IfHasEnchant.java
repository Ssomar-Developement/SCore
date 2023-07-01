package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.list.ListEnchantAndLevelFeature;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IfHasEnchant extends ItemConditionFeature<ListEnchantAndLevelFeature, IfHasEnchant> {

    public IfHasEnchant(FeatureParentInterface parent) {
        super(parent, "ifHasEnchant", "If has enchant", new String[]{}, Material.ANVIL, false);
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
                if (!enchants.containsKey(enchant) || !Objects.equals(condition.get(enchant), enchants.get(enchant))) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfHasEnchant getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEnchantAndLevelFeature(this, "ifHasEnchant", new HashMap<>(), "If has enchant", new String[]{"&7If has enchant condition"}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfHasEnchant getNewInstance(FeatureParentInterface parent) {
        return new IfHasEnchant(parent);
    }
}
