package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfIsNotInTheAir extends PlayerCondition<Boolean> {


    public IfIsNotInTheAir() {
        super(ConditionType.BOOLEAN, "ifIsNotInTheAir", "If is not in the air", new String[]{}, false, " &cYou must not be in the air to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition()) {
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();
            if (type.equals(Material.AIR)) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
