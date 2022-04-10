package com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.custom;

import com.ssomar.score.sobject.sactivator.conditions.condition.ConditionType;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AroundBlockConditions extends BlockCondition<List<AroundBlockCondition>> {

    public AroundBlockConditions() {
        super(ConditionType.CUSTOM_AROUND_BLOCK, "blockAroundCdts", "Block Around Conditions", new String[]{}, new ArrayList<>(), "");
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender) {
        for(AroundBlockCondition bAC : getCondition()) {
            if(!bAC.verif(b, playerOpt, messangeSender)) return false;
        }
        return true;
    }
}
