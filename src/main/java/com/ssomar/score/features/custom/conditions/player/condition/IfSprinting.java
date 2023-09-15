package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfSprinting extends PlayerConditionFeature<BooleanFeature, IfSprinting> {


    public IfSprinting(FeatureParentInterface parent) {
        super(parent, "ifSprinting", "If sprinting", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !player.isSprinting()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfSprinting getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifSprinting", false, "If sprinting", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfSprinting getNewInstance(FeatureParentInterface parent) {
        return new IfSprinting(parent);
    }
}
