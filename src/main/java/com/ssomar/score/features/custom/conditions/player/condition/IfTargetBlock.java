package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfTargetBlock extends PlayerConditionFeature<ListDetailedMaterialFeature, IfTargetBlock> {

    public IfTargetBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifTargetBlock);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Block block = player.getTargetBlock(null, 5);
            Material material = block.getType();
            /* take only the fix block, not hte falling block */
            if ((material.equals(Material.WATER) || material.equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
                runInvalidCondition(request);
                return false;
            }

            if(!getCondition().verifBlock(block)){
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfTargetBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListDetailedMaterialFeature(this, new ArrayList<>(), FeatureSettingsSCore.ifTargetBlock, true, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getCurrentValues().size() > 0;
    }

    @Override
    public IfTargetBlock getNewInstance(FeatureParentInterface parent) {
        return new IfTargetBlock(parent);
    }
}
