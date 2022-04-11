package com.ssomar.score.conditions.condition.playercondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.playercondition.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfCursorDistance extends PlayerCondition<String> {


    public IfCursorDistance() {
        super(ConditionType.NUMBER_CONDITION, "ifCursorDistance", "If cursor distance", new String[]{}, "",  " &cCursor distance is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender) {
        if (getCondition() != null && getCondition().length() > 0){
            Block block = player.getTargetBlock(null, 200);
            if(block.getType().equals(Material.AIR)) return false;

            if(!StringCalculation.calculation(getCondition(), player.getLocation().distance(block.getLocation()))){
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
