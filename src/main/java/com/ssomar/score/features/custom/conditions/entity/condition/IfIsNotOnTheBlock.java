package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;

public class IfIsNotOnTheBlock extends EntityConditionFeature<MaterialAndTagsGroupFeature, IfIsNotOnTheBlock> {


    public IfIsNotOnTheBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsNotOnTheBlock);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            Location pLoc = entity.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();
            BlockData blockData = block.getBlockData();

            if (getCondition().isValid(block)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsNotOnTheBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new MaterialAndTagsGroupFeature(this, FeatureSettingsSCore.ifIsNotOnTheBlock, true, false, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().getMaterialAndTags().size() > 0;
    }

    @Override
    public IfIsNotOnTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfIsNotOnTheBlock(parent);
    }

}
