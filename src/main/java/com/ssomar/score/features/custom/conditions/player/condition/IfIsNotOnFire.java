package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfIsNotOnFire extends PlayerConditionFeature<BooleanFeature, IfIsNotOnFire> {

    public IfIsNotOnFire(FeatureParentInterface parent) {
        super(parent, "ifIsNotOnFire", "If is not on fire", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && player.getFireTicks() > 0) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfIsNotOnFire getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifIsNotOnFire", false, "If is not on fire", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfIsNotOnFire getNewInstance(FeatureParentInterface parent) {
        return new IfIsNotOnFire(parent);
    }
}
