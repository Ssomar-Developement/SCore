package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.messages.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfContainerEmpty extends BlockConditionFeature<BooleanFeature, IfContainerEmpty> {

    public IfContainerEmpty(FeatureParentInterface parent) {
        super(parent, "ifContainerEmpty", "If container empty", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && b.getState() instanceof Container && !((Container)b.getState()).getInventory().isEmpty()) {
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
    public IfContainerEmpty getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifContainerEmpty", false, "If container empty", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfContainerEmpty getNewInstance(FeatureParentInterface parent) {
        return new IfContainerEmpty(parent);
    }
}
