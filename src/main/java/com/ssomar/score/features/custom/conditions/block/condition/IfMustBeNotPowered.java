package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;

public class IfMustBeNotPowered extends BlockConditionFeature<BooleanFeature, IfMustBeNotPowered> {


    public IfMustBeNotPowered(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifMustBeNotPowered);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        Block b = request.getBlock();
        if (getCondition().getValue(request.getSp()) && b.getBlockData() instanceof Powerable) {
            Powerable power = (Powerable) b.getBlockData();
            if (power.isPowered()) {
                runInvalidCondition(request);
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
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifMustBeNotPowered, true));
    }

    @Override
    public IfMustBeNotPowered getNewInstance(FeatureParentInterface parent) {
        return new IfMustBeNotPowered(parent);
    }
}
