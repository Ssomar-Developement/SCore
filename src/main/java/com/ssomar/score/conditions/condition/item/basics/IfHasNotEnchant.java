package com.ssomar.score.conditions.condition.item.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.item.ItemCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class IfHasNotEnchant extends ItemCondition<Map<Enchantment, Integer>> {


    public IfHasNotEnchant() {
        super(ConditionType.MAP_ENCHANT_AMOUNT, "ifHasNotEnchant", "If has not enchant", new String[]{}, new HashMap<>(), " &cThis item must have the good enchantments to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

        if(getCondition() != null && getCondition().size() != 0){

            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if(hasItemMeta) itemMeta = itemStack.getItemMeta();

            if(!hasItemMeta){
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
            Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
            for(Enchantment enchant : getCondition().keySet()){
                if(enchants.containsKey(enchant) && getCondition().get(enchant).equals(enchants.get(enchant))){
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }

        return true;
    }
}
