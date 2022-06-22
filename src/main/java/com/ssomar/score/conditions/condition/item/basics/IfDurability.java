package com.ssomar.score.conditions.condition.item.basics;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.item.ItemCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.net.ServerSocket;
import java.util.Optional;

public class IfDurability extends ItemCondition<String, String> {


    public IfDurability() {
        super(ConditionType.NUMBER_CONDITION, "ifDurability", "If durability", new String[]{}, "", " &cThis item must have a valid durability to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

        if(isDefined()) {
            if(!StringCalculation.calculation(getAllCondition(messageSender.getSp()), itemStack.getType().getMaxDurability()-itemStack.getDurability())) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }

        return true;
    }
}
