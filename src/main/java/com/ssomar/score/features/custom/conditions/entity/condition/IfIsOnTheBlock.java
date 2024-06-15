package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Optional;

public class IfIsOnTheBlock extends EntityConditionFeature<DetailedBlocks, IfIsOnTheBlock> {

    public IfIsOnTheBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsOnTheBlock);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity player = request.getEntity();
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();

            if (!getCondition().isValid(block, Optional.empty(), null, new StringPlaceholder())) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsOnTheBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new DetailedBlocks(this, FeatureSettingsSCore.ifIsOnTheBlock, true ,true, true));

        // new MaterialAndTagsGroupFeature(this, "ifIsOnTheBlock", "If is on the block", new String[]{}, true, false, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getBlocks().getValues().size() > 0;
    }

    @Override
    public IfIsOnTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfIsOnTheBlock(parent);
    }
}