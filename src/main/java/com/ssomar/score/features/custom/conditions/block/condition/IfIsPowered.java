package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;

public class IfIsPowered extends BlockConditionFeature<BooleanFeature, IfIsPowered> {

    public IfIsPowered(FeatureParentInterface parent) {
        super(parent, "ifIsPowered", "If is powered", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition()) {
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
        setCondition(new BooleanFeature(getParent(), "ifIsPowered", false, "If is powered", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfIsPowered getNewInstance(FeatureParentInterface parent) {
        return new IfIsPowered(parent);
    }
}
