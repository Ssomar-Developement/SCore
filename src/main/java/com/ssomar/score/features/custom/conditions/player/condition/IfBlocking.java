package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;

public class IfBlocking extends PlayerConditionFeature<BooleanFeature, IfBlocking> {

    public IfBlocking(FeatureParentInterface parent) {
        super(parent, "ifBlocking", "If blocking", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition() && !request.getPlayer().isBlocking()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfBlocking getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifBlocking", false, "If blocking", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfBlocking getNewInstance(FeatureParentInterface parent) {
        return new IfBlocking(parent);
    }
}
