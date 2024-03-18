package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

public class IfNotHasPermission extends PlayerConditionFeature<ListUncoloredStringFeature, IfNotHasPermission> {

    public IfNotHasPermission(FeatureParentInterface parent) {
        super(parent, "ifNotHasPermission", "If not has permission", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            for (String perm : getCondition().getValue(request.getSp())) {
                if (player.hasPermission(perm)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfNotHasPermission getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), "ifNotHasPermission", new ArrayList<>(), "If has not permission", new String[]{}, Material.ANVIL, false, true, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotHasPermission getNewInstance(FeatureParentInterface parent) {
        return new IfNotHasPermission(parent);
    }
}
