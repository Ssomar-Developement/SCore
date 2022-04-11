package com.ssomar.score.conditions.condition.blockcondition.basics;

import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.executableblocks.blocks.placedblocks.LocationConverter;
import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfUsage extends BlockCondition<String> {

    public IfUsage() {
        super(ConditionType.NUMBER_CONDITION, "ifUsage", "If usage", new String[]{}, "", " &cThis block must have the valid usage to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender) {
        if(!getCondition().equals("") && SCore.hasExecutableBlocks) {

            Location bLoc = LocationConverter.convert(b.getLocation(), false, false);
            ExecutableBlockPlaced executableBlockPlaced = ExecutableBlockPlacedManager.getInstance().getExecutableBlockPlaced(bLoc);
            if(executableBlockPlaced != null) {
                int usage = executableBlockPlaced.getUsage();

                if (!StringCalculation.calculation(getCondition(), usage)) {
                    sendErrorMsg(playerOpt, messangeSender);
                    return false;
                }
            }
        }
        return true;
    }
}
