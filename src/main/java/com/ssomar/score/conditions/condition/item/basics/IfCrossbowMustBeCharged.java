package com.ssomar.score.conditions.condition.item.basics;

import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.item.ItemCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class IfCrossbowMustBeCharged extends ItemCondition<Boolean> {


    public IfCrossbowMustBeCharged() {
        super(ConditionType.BOOLEAN, "ifCrossbowMustBeCharged", "If crossbow must be charged", new String[]{}, false, " &cThis crossbow must be charged to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

        if(getCondition()  && itemStack.getType().toString().contains("CROSSBOW")){

            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if(hasItemMeta) itemMeta = itemStack.getItemMeta();

            if(hasItemMeta && itemMeta instanceof CrossbowMeta){
                CrossbowMeta cMeta = (CrossbowMeta)itemMeta;
                boolean charged = cMeta.hasChargedProjectiles();

                if(!charged){
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }

        return true;
    }
}
