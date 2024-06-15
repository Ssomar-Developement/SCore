package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

public class IfContainerNotEmpty extends BlockConditionFeature<BooleanFeature, IfContainerNotEmpty> {

    public IfContainerNotEmpty(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifContainerNotEmpty);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        Block b = request.getBlock();
        if (getCondition().getValue(request.getSp()) && b.getState() instanceof Container && ((Container)b.getState()).getInventory().isEmpty()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfContainerNotEmpty getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifContainerNotEmpty, true));
    }

    @Override
    public IfContainerNotEmpty getNewInstance(FeatureParentInterface parent) {
        return new IfContainerNotEmpty(parent);
    }
}
