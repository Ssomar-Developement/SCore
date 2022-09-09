package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfContainerNotEmpty extends BlockConditionFeature<BooleanFeature, IfContainerNotEmpty> {

    public IfContainerNotEmpty(FeatureParentInterface parent) {
        super(parent, "ifContainerNotEmpty", "If container not empty", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && b.getState() instanceof Container && ((Container)b.getState()).getInventory().isEmpty()) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfContainerNotEmpty getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifContainerNotEmpty", false,"If container not empty", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfContainerNotEmpty getNewInstance(FeatureParentInterface parent) {
        return new IfContainerNotEmpty(parent);
    }
}
