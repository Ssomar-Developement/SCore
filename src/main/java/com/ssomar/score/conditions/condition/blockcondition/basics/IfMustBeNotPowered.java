package com.ssomar.score.conditions.condition.blockcondition.basics;

import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfMustBeNotPowered extends BlockCondition<Boolean> {


    public IfMustBeNotPowered() {
        super(ConditionType.BOOLEAN, "ifMustBeNotPowered", "If must be not powered", new String[]{}, false, " &cThe block must be not powered by redstone to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender) {
        if(getCondition() && b.getBlockData() instanceof Powerable) {
            Powerable power = (Powerable)b.getBlockData();
            if(power.isPowered()) {
                sendErrorMsg(playerOpt, messangeSender);
                return false;
            }
        }
        return true;
    }
}
