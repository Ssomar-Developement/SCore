package com.ssomar.score.conditions.condition.customei.basics;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.customei.CustomEICondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;
import java.util.UUID;

public class IfOwnerOfTheEI extends CustomEICondition<Boolean,String> {


    public IfOwnerOfTheEI() {
        super(ConditionType.BOOLEAN, "ifOwnerOfTheEI", "If owner of the EI", new String[]{}, false, " &cYou must be the owner of the item to active the activator: &6%activator% &c!");
    }

    @Override
    public boolean verifCondition(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp())) {
            if(itemStack.hasItemMeta()) {
                ItemMeta iM = itemStack.getItemMeta();

                NamespacedKey key = new NamespacedKey(ExecutableItems.getPluginSt(), "EI-OWNER");
                String uuidStr = iM.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                boolean invalid = false;
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(uuidStr);
                }catch(Exception e ) {
                    invalid = true;
                }
                if(invalid || !uuid.equals(player.getUniqueId())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }
}
