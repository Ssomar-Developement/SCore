package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfNotSneaking extends PlayerConditionFeature<BooleanFeature, IfNotSneaking> {

    public IfNotSneaking(FeatureParentInterface parent) {
        super(parent, "ifNotSneaking", "If not sneaking", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && player.isSneaking()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfNotSneaking getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNotSneaking", false, "If not sneaking", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotSneaking getNewInstance(FeatureParentInterface parent) {
        return new IfNotSneaking(parent);
    }
}
