package com.ssomar.score.conditions.condition.blockcondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.usedapi.MyCoreProtectAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfMustBeNatural extends BlockCondition<Boolean,String> {


    public IfMustBeNatural() {
        super(ConditionType.BOOLEAN, "ifMustBeNatural", "if must be natural", new String[]{}, false, " &cThe block must be natural to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender) {
        if(getAllCondition(messangeSender.getSp())) {
            if(!MyCoreProtectAPI.isNaturalBlock(b)) {
                sendErrorMsg(playerOpt, messangeSender);
                return false;
            }
        }
        return true;
    }
}
