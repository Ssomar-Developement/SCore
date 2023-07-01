package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

public class IfContainerNotEmpty extends BlockConditionFeature<BooleanFeature, IfContainerNotEmpty> {

    public IfContainerNotEmpty(FeatureParentInterface parent) {
        super(parent, "ifContainerNotEmpty", "If container not empty", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        Block b = request.getBlock();
        if (hasCondition() && b.getState() instanceof Container && ((Container)b.getState()).getInventory().isEmpty()) {
            runInvalidCondition(request);
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
