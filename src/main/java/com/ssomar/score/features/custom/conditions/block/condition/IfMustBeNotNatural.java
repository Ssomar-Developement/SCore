package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.MyCoreProtectAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class IfMustBeNotNatural extends BlockConditionFeature<BooleanFeature, IfMustBeNotNatural> {

    public IfMustBeNotNatural(FeatureParentInterface parent) {
        super(parent, "ifMustBeNotNatural", "If must be not natural", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Block b = request.getBlock();
            if (MyCoreProtectAPI.isNaturalBlock(b)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeNotNatural getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifMustBeNotNatural", false, "If must be not natural", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfMustBeNotNatural getNewInstance(FeatureParentInterface parent) {
        return new IfMustBeNotNatural(parent);
    }

}
