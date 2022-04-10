package com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.basics;

import com.ssomar.score.sobject.sactivator.conditions.condition.ConditionType;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfPlayerMustBeOnTheBlock extends BlockCondition<Boolean> {


    public IfPlayerMustBeOnTheBlock() {
        super(ConditionType.BOOLEAN, "ifPlayerMustBeOnTheBlock", "If player must be on the block", new String[]{}, false, " &cA player must be on the block to active the activator: &6%activator% &cof this item!");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender) {
        if(getCondition()){
            boolean onBlock = false;
            Location bLoc = b.getLocation();
            bLoc = bLoc.add(0.5,1,0.5);
            for(Player pl : Bukkit.getServer().getOnlinePlayers()){
                Location pLoc = pl.getLocation();
                if(bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
                    if (bLoc.distance(pLoc) < 1.135) {
                        onBlock = true;
                        break;
                    }
                }
            }
            if(!onBlock){
                sendErrorMsg(playerOpt, messangeSender);
                return false;
            }
        }
        return true;
    }
}
