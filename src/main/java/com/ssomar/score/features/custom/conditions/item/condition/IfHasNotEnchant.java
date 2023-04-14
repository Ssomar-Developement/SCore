package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.types.list.ListEnchantAndLevelFeature;
import com.ssomar.score.utils.messages.SendMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IfHasNotEnchant extends ItemConditionFeature<ListEnchantAndLevelFeature, IfHasNotEnchant> {

    public IfHasNotEnchant(FeatureParentInterface parent) {
        super(parent, "ifHasNotEnchant", "If has not enchant", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if (hasCondition()) {

            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if (hasItemMeta) itemMeta = itemStack.getItemMeta();

            if (!hasItemMeta) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
            Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
            Map<Enchantment, Integer> condition = getCondition().getValue();
            for (Enchantment enchant : condition.keySet()) {
                if (enchants.containsKey(enchant) && condition.get(enchant).equals(enchants.get(enchant))) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
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
        setCondition(new ListEnchantAndLevelFeature(this, "ifHasNotEnchant", new HashMap<>(), "If has not enchant", new String[]{"&7If has not enchant condition"}, Material.ANVIL, false, true));
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
