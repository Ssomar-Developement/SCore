package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListWorldFeature;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfInWorld extends PlayerConditionFeature<ListWorldFeature, IfInWorld> {

    public IfInWorld(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifInWorld);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            boolean notValid = true;
            for (String s : getCondition().getValue()) {
                if (player.getWorld().getName().equalsIgnoreCase(s)) {
                    notValid = false;
                    break;
                }
            }
            if (notValid) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfInWorld getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListWorldFeature(getParent(),  new ArrayList<>(), FeatureSettingsSCore.ifInWorld, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfInWorld getNewInstance(FeatureParentInterface parent) {
        return new IfInWorld(parent);
    }
}
