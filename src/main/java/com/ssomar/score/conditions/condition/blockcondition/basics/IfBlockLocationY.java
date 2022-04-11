package com.ssomar.score.conditions.condition.blockcondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfBlockLocationY extends BlockCondition<String> {

    public IfBlockLocationY() {
        super(ConditionType.NUMBER_CONDITION, "ifBlockLocationY", "If block location Y", new String[]{}, "", " &cThe block location Y is invalid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender) {
        if(!getCondition().equals("") && !StringCalculation.calculation(getCondition(), b.getLocation().getY())) {
            sendErrorMsg(playerOpt, messangeSender);
            return false;
        }
        return true;
    }
}
