package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListRegionStringFeature;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfInRegion extends PlayerConditionFeature<ListRegionStringFeature, IfInRegion> {

    public IfInRegion(FeatureParentInterface parent) {
        super(parent, "ifInRegion", "If in region", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (SCore.hasWorldGuard) {
            Player player = request.getPlayer();
            if (hasCondition() && !new WorldGuardAPI().isInRegion(player, getCondition().getValue())) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfInRegion getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListRegionStringFeature(getParent(), "ifInRegion", new ArrayList<>(), "If in region", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfInRegion getNewInstance(FeatureParentInterface parent) {
        return new IfInRegion(parent);
    }
}
