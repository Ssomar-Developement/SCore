package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfSwimming extends PlayerConditionFeature<BooleanFeature, IfSwimming> {

    public IfSwimming(FeatureParentInterface parent) {
        super(parent, "ifSwimming", "If swimming", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !player.isSwimming()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfSwimming getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifSwimming", false, "If swimming", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfSwimming getNewInstance(FeatureParentInterface parent) {
        return new IfSwimming(parent);
    }
}
