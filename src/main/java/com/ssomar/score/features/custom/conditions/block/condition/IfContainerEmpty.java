package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

public class IfContainerEmpty extends BlockConditionFeature<BooleanFeature, IfContainerEmpty> {

    public IfContainerEmpty(FeatureParentInterface parent) {
        super(parent, "ifContainerEmpty", "If container empty", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        Block b = request.getBlock();
        if (hasCondition() && b.getState() instanceof Container && !((Container)b.getState()).getInventory().isEmpty()) {
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
