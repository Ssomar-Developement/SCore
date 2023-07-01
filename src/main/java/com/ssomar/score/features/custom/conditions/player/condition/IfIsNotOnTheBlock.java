package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.custom.materialwithgroupsandtags.group.MaterialAndTagsGroupFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class IfIsNotOnTheBlock extends PlayerConditionFeature<MaterialAndTagsGroupFeature, IfIsNotOnTheBlock> {

    public IfIsNotOnTheBlock(FeatureParentInterface parent) {
        super(parent, "ifIsNotOnTheBlock", "If is not on the block", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();

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
        setCondition(new MaterialAndTagsGroupFeature(this, "ifIsNotOnTheBlock", "If is not on the block", new String[]{}, true, false, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getMaterialAndTags().size() > 0;
    }

    @Override
    public IfIsNotOnTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfIsNotOnTheBlock(parent);
    }
}
