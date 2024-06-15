package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.MyCoreProtectAPI;
import org.bukkit.block.Block;

public class IfMustBeNatural extends BlockConditionFeature<BooleanFeature, IfMustBeNatural> {

    public IfMustBeNatural(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifMustBeNatural);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Block b = request.getBlock();
            if (!MyCoreProtectAPI.isNaturalBlock(b)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeNatural getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifMustBeNatural, true));
    }

    @Override
    public IfMustBeNatural getNewInstance(FeatureParentInterface parent) {
        return new IfMustBeNatural(parent);
    }

}
