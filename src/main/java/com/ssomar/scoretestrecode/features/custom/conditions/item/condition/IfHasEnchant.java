package com.ssomar.scoretestrecode.features.custom.conditions.item.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListEnchantAndLevelFeature;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class IfHasEnchant extends ItemConditionFeature<ListEnchantAndLevelFeature, IfHasEnchant> {

    public IfHasEnchant(FeatureParentInterface parent) {
        super(parent, "ifHasEnchant", "If has enchant", new String[]{}, Material.ANVIL, false);
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
                if (!enchants.containsKey(enchant) || !Objects.equals(condition.get(enchant), enchants.get(enchant))) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
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
        setCondition(new ListEnchantAndLevelFeature(this, "ifHasEnchant", new HashMap<>(), "If has enchant", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfHasEnchant getNewInstance() {
        return new IfHasEnchant(getParent());
    }
}
