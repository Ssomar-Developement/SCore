package com.ssomar.scoretestrecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfMustBeNotPowered extends BlockConditionFeature<BooleanFeature, IfMustBeNotPowered> {


    public IfMustBeNotPowered(FeatureParentInterface parent) {
         super(parent, "ifMustBeNotPowered","If must be not powered", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender, Event event) {
        if(hasCondition() && b.getBlockData() instanceof Powerable) {
            Powerable power = (Powerable)b.getBlockData();
            if(power.isPowered()) {
                sendErrorMsg(playerOpt, messangeSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeNotPowered getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifMustBeNotPowered", false,"If must be not powered", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfMustBeNotPowered getNewInstance() {
        return new IfMustBeNotPowered(getParent());
    }
}
