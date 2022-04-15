package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotTargetBlock extends PlayerCondition<List<Material>, List<String>> {


    public IfNotTargetBlock() {
        super(ConditionType.LIST_MATERIAL, "ifNotTargetBlock", "If not target block", new String[]{}, new ArrayList<>(), " &cYou don't target the good type of block to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            Block block = player.getTargetBlock(null, 5);
            /* take only the fix block, not hte falling block */
            if ((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
            if (getAllCondition(messageSender.getSp()).contains(block.getType())) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
