package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.TownyToolAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfPlayerMustBeInHisTown extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeInHisTown> {

    public IfPlayerMustBeInHisTown(FeatureParentInterface parent) {
        super(parent, "ifPlayerMustBeInHisTown", "If player must be in his town", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            if (SCore.hasTowny) {
                if (!TownyToolAPI.playerIsInHisTown(player, player.getLocation())) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeInHisTown getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlayerMustBeInHisTown", false, "If player must be in his town", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfPlayerMustBeInHisTown getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeInHisTown(parent);
    }
}
