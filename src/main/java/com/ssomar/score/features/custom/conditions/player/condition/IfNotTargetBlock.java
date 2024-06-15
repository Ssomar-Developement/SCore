package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListMaterialFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

// TODO PASS TO ListMaterialGroupFeature
public class IfNotTargetBlock extends PlayerConditionFeature<ListMaterialFeature, IfNotTargetBlock> {

    public IfNotTargetBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNotTargetBlock);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Block block = player.getTargetBlock(null, 5);
            /* take only the fix block, not hte falling block */
            if ((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
                runInvalidCondition(request);
                return false;
            }
            if (getCondition().getValue().contains(block.getType())) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNotTargetBlock getValue() {
        return this;
    }


    @Override
    public void subReset() {
        setCondition(new ListMaterialFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifNotTargetBlock, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotTargetBlock getNewInstance(FeatureParentInterface parent) {
        return new IfNotTargetBlock(parent);
    }
}
