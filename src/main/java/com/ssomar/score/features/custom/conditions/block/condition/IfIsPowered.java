package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;

public class IfIsPowered extends BlockConditionFeature<BooleanFeature, IfIsPowered> {

    public IfIsPowered(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsPowered);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Block b = request.getBlock();
            //SsomarDev.testMsg("block: "+b.getType()+ "   isBlockpowered: "+b.isBlockPowered()+ " is Powerable: "+(b.getBlockData() instanceof Powerable)+ "power: "+b.getBlockPower());
            boolean notPowered = !b.isBlockPowered() && b.getBlockPower() == 0;

            if (b.getBlockData() instanceof Powerable) {
                Powerable power = (Powerable) b.getBlockData();
                notPowered = !power.isPowered();
            }

            if (notPowered) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsPowered getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifIsPowered, true));
    }

    @Override
    public IfIsPowered getNewInstance(FeatureParentInterface parent) {
        return new IfIsPowered(parent);
    }
}
