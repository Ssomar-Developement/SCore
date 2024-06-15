package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class IfCanBreakTargetedBlock extends PlayerConditionFeature<BooleanFeature, IfCanBreakTargetedBlock> {

    public IfCanBreakTargetedBlock(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifCanBreakTargetedBlock);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Player player = request.getPlayer();
            Block block = player.getTargetBlock(null, 5);
            if (!SafeBreak.verifSafeBreak(player.getUniqueId(), block)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfCanBreakTargetedBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifCanBreakTargetedBlock, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfCanBreakTargetedBlock getNewInstance(FeatureParentInterface parent) {
        return new IfCanBreakTargetedBlock(parent);
    }
}
