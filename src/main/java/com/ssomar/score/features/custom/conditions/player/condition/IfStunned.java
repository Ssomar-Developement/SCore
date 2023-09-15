package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.commands.runnable.player.events.StunEvent;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfStunned extends PlayerConditionFeature<BooleanFeature, IfStunned> {


    public IfStunned(FeatureParentInterface parent) {
        super(parent, "ifStunned", "If stunned", new String[]{"&7&oBy the custom player", "&7&ocommand &eSTUN_ENABLE"}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !StunEvent.stunPlayers.containsKey(player.getUniqueId())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfStunned getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifStunned", false, "If stunned", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfStunned getNewInstance(FeatureParentInterface parent) {
        return new IfStunned(parent);
    }
}
