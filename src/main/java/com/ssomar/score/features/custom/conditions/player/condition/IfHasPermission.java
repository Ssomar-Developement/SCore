package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

public class IfHasPermission extends PlayerConditionFeature<ListUncoloredStringFeature, IfHasPermission> {


    public IfHasPermission(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifHasPermission);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition()) {
            boolean valid = true;
            for (String perm : getCondition().getValue(request.getSp())) {
                if (!player.hasPermission(perm)) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfHasPermission getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifHasPermission, true, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfHasPermission getNewInstance(FeatureParentInterface parent) {
        return new IfHasPermission(parent);
    }
}
