package com.ssomar.score.conditions.condition.blockcondition.basics;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfIsPowered extends BlockCondition<Boolean, String> {


    public IfIsPowered() {
        super(ConditionType.BOOLEAN, "ifIsPowered", "If is powered", new String[]{}, false, " &cThe block must be powered by redstone to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender) {
        if(getAllCondition(messageSender.getSp())) {
            //SsomarDev.testMsg("block: "+b.getType()+ "   isBlockpowered: "+b.isBlockPowered()+ " is Powerable: "+(b.getBlockData() instanceof Powerable)+ "power: "+b.getBlockPower());
            boolean notPowered = !b.isBlockPowered() && b.getBlockPower() == 0;

            if(b.getBlockData() instanceof Powerable) {
                Powerable power = (Powerable)b.getBlockData();
                notPowered = !power.isPowered();
            }

            if(notPowered) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }
}
