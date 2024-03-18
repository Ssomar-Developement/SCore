package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfGliding extends PlayerConditionFeature<BooleanFeature, IfGliding> {

    public IfGliding(FeatureParentInterface parent) {
        super(parent, "ifGliding", "If gliding", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (getCondition().getValue(request.getSp()) && !player.isGliding()) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfGliding getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifGliding", false, "If gliding", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfGliding getNewInstance(FeatureParentInterface parent) {
        return new IfGliding(parent);
    }
}
