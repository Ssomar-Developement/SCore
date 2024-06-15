package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class IfPlayerMustBeOnTheBlock extends BlockConditionFeature<BooleanFeature, IfPlayerMustBeOnTheBlock> {

    public IfPlayerMustBeOnTheBlock(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifPlayerMustBeOnTheBlock);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Block b = request.getBlock();
            boolean onBlock = false;
            Location bLoc = b.getLocation();
            bLoc = bLoc.add(0.5, 1, 0.5);
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                Location pLoc = pl.getLocation();
                if (bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
                    if (bLoc.distance(pLoc) < 1.135) {
                        onBlock = true;
                        break;
                    }
                }
            }
            if (!onBlock) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeOnTheBlock getValue() {
        return this;
    }


    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifPlayerMustBeOnTheBlock, true));
    }

    @Override
    public IfPlayerMustBeOnTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeOnTheBlock(parent);
    }
}
