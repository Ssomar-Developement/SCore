package com.ssomar.score.conditions.condition.blockcondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlantFullyGrown extends BlockCondition<Boolean> {

    public IfPlantFullyGrown() {
        super(ConditionType.BOOLEAN, "ifPlantFullyGrown", "If plant fully grown", new String[]{}, false, " &cThe plant must be fully grown to active the activator: &6%activator% &cof this item!");
    }


    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender) {

        if(getCondition() && b.getState().getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) b.getState().getBlockData();
            int age = ageable.getAge();
            if(age != ageable.getMaximumAge()) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
