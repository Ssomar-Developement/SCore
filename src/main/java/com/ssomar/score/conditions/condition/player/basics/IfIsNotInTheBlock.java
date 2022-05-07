package com.ssomar.score.conditions.condition.player.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfIsNotInTheBlock extends PlayerCondition<List<Material>, List<String>> {


    public IfIsNotInTheBlock() {
        super(ConditionType.LIST_MATERIAL, "ifIsNotInTheBlock", "If is not in the block", new String[]{}, new ArrayList<>(), " &cYou are not in the good type of block to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (isDefined()) {
            Location pLoc = player.getLocation();
            Block block = pLoc.getBlock();
            Material type = block.getType();

            Block block2 = pLoc.getBlock().getRelative(BlockFace.UP);
            Material type2 = block2.getType();

            if (getAllCondition(messageSender.getSp()).contains(type) || getAllCondition(messageSender.getSp()).contains(type2)) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
