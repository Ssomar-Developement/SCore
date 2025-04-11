package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.block.Block;

public class IfMustBeEB extends BlockConditionFeature<BooleanFeature, IfMustBeEB> {

    public IfMustBeEB(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifMustBeEB);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Block b = request.getBlock();

            // Check if the block is an executable block
            if (!ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(b).isPresent()){
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeEB getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifMustBeEB));
    }

    @Override
    public IfMustBeEB getNewInstance(FeatureParentInterface parent) {
        return new IfMustBeEB(parent);
    }

}
