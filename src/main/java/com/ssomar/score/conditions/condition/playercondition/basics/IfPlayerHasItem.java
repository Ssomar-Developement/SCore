package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IfPlayerHasItem extends PlayerCondition<Map<Material, Integer>> {


    public IfPlayerHasItem() {
        super(ConditionType.MAP_MATERIAL_AMOUNT, "ifPlayerHasItem", "If player has item", new String[]{}, new HashMap<>(), " &cYou don't have all correct Items to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().size() > 0) {
            ItemStack[] content = player.getInventory().getContents();

            Map<Material, Integer> verifI = new HashMap<>(getCondition());

            int cpt = -1;
            for (ItemStack is : content) {
                cpt++;
                if (is == null) continue;

                if (verifI.containsKey(is.getType()) && ((verifI.get(is.getType()) == cpt) || verifI.get(is.getType()) == -1 && cpt == player.getInventory().getHeldItemSlot())) {
                    verifI.remove(is.getType());
                }
            }

            if (!verifI.isEmpty()) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
