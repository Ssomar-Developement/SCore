package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class IfIsNotInTheBlock extends PlayerConditionFeature<MaterialAndTagsGroupFeature, IfIsNotInTheBlock> {

    public IfIsNotInTheBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsNotInTheBlock);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Location pLoc = player.getLocation();
            Block block = pLoc.getBlock();
            Material type = block.getType();

            Block block2 = pLoc.getBlock().getRelative(BlockFace.UP);
            Material type2 = block2.getType();

            if (getCondition().isValid(block) || getCondition().isValid(block2)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsNotInTheBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new MaterialAndTagsGroupFeature(this, FeatureSettingsSCore.ifIsNotInTheBlock, true, false, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getMaterialAndTags().size() > 0;
    }

    @Override
    public IfIsNotInTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfIsNotInTheBlock(parent);
    }
}
