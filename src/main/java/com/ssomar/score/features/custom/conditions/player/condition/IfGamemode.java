package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListGameModeFeature;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfGamemode extends PlayerConditionFeature<ListGameModeFeature, IfGamemode> {

    public IfGamemode(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifGameMode);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            boolean notValid = !getCondition().isValid(player.getGameMode());
            if (notValid) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfGamemode getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListGameModeFeature(getParent(),  new ArrayList<>(), FeatureSettingsSCore.ifGameMode));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfGamemode getNewInstance(FeatureParentInterface parent) {
        return new IfGamemode(parent);
    }
}
