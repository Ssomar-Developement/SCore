package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListBiomeFeature;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfInBiome extends PlayerConditionFeature<ListBiomeFeature, IfInBiome> {

    public IfInBiome(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifInBiome);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            boolean notValid = !getCondition().isValid(player.getLocation());
            if (notValid) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfInBiome getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListBiomeFeature(getParent(),  new ArrayList<>(), FeatureSettingsSCore.ifInBiome, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfInBiome getNewInstance(FeatureParentInterface parent) {
        return new IfInBiome(parent);
    }
}
