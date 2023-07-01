package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfNotGliding extends PlayerConditionFeature<BooleanFeature, IfNotGliding> {

    public IfNotGliding(FeatureParentInterface parent) {
        super(parent, "ifNotGliding", "If not gliding", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && player.isGliding()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfNotGliding getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNotGliding", false, "If not gliding", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNotGliding getNewInstance(FeatureParentInterface parent) {
        return new IfNotGliding(parent);
    }
}
