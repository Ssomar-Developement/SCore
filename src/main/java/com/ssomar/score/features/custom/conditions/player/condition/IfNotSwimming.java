package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfNotSwimming extends PlayerConditionFeature<BooleanFeature, IfNotSwimming> {

    public IfNotSwimming(FeatureParentInterface parent) {
        super(parent, "ifNotSwimming", "If not swimming", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && player.isSwimming()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfNotSwimming getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNotSwimming", false, "If not swimming", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNotSwimming getNewInstance(FeatureParentInterface parent) {
        return new IfNotSwimming(parent);
    }
}
