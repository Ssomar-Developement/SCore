package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeature;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.Optional;

public class IfIsInTheBlock extends PlayerConditionFeature<DetailedBlocks, IfIsInTheBlock> {

    public IfIsInTheBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsInTheBlock);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Location pLoc = player.getLocation();
            Block block = pLoc.getBlock();
            //Material type = block.getType();

            Block block2 = pLoc.getBlock().getRelative(BlockFace.UP);
            //Material type2 = block2.getType();

            if (!getCondition().isValid(block, Optional.empty(), null, new StringPlaceholder())
                && !getCondition().isValid(block2, Optional.empty(), null, new StringPlaceholder())) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsInTheBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new DetailedBlocks(this, FeatureSettingsSCore.ifIsInTheBlock, true, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getBlocks().getValues().size() > 0;
    }

    @Override
    public IfIsInTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfIsInTheBlock(parent);
    }
}
