package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfIsOnTheBlock extends PlayerCondition<List<Material>> {


    public IfIsOnTheBlock() {
        super(ConditionType.LIST_MATERIAL, "ifIsOnTheBlock", "If is on the block", new String[]{}, new ArrayList<>(), " &cYou are not on the good type of block to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().size() > 0) {
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();

            if (!getCondition().contains(type)) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
