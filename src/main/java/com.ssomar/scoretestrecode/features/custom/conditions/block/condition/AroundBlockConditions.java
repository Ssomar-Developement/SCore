package com.ssomar.scoretestrecode.features.custom.conditions.block.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.aroundblock.aroundblock.AroundBlockFeature;
import com.ssomar.scoretestrecode.features.custom.aroundblock.group.AroundBlockGroupFeature;
import com.ssomar.scoretestrecode.features.custom.aroundblock.group.AroundBlockGroupFeatureEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class AroundBlockConditions extends BlockConditionFeature<AroundBlockGroupFeature, AroundBlockConditions> {

    public AroundBlockConditions(FeatureParentInterface parent) {
        super(parent, "blockAroundCdts", "Block Around Conditions", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getAroundBlockGroup().size() > 0;
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
           for(AroundBlockFeature cdt : getCondition().getAroundBlockGroup().values()){
               if (!cdt.verif(b, playerOpt, messageSender)) {
                   sendErrorMsg(playerOpt, messageSender);
                   cancelEvent(event);
                   return false;
               }
           }
        }
        return true;
    }

    @Override
    public AroundBlockConditions getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new AroundBlockGroupFeature(this, true));
    }

    @Override
    public AroundBlockConditions getNewInstance(FeatureParentInterface parent) {
        return new AroundBlockConditions(parent);
    }
}
