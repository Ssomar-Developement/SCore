package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListEntityTypeFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfPlayerNotMounts extends PlayerConditionFeature<ListEntityTypeFeature, IfPlayerNotMounts> {

    public IfPlayerNotMounts(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerNotMounts);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Entity vehicle;
            boolean error = false;
            if ((vehicle = player.getVehicle()) != null) {
                if (getCondition().getValue().contains(vehicle.getType())) error = true;
            }

            if (error) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerNotMounts getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEntityTypeFeature(this,  new ArrayList<>(), FeatureSettingsSCore.ifPlayerNotMounts, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerNotMounts getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerNotMounts(parent);
    }
}
