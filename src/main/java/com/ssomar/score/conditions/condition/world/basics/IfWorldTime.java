package com.ssomar.score.conditions.condition.world.basics;

import com.ssomar.score.conditions.condition.world.WorldCondition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfWorldTime extends WorldCondition<String, String> {


    public IfWorldTime() {
        super(ConditionType.NUMBER_CONDITION, "ifWorldTime", "If world time", new String[]{}, "", " &cThe world time is not valid to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(World world, Optional<Player> playerOpt, SendMessage messageSender) {
        if(isDefined() && !StringCalculation.calculation(getAllCondition(messageSender.getSp()), world.getTime())) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }
}
