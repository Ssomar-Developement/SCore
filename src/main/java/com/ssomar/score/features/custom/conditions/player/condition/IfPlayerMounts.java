package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.list.ListEntityTypeFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IfPlayerMounts extends PlayerConditionFeature<ListEntityTypeFeature, IfPlayerMounts> {

    public IfPlayerMounts(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifPlayerMounts);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Entity vehicle;
            boolean error = false;
            Player player = request.getPlayer();
            if ((vehicle = player.getVehicle()) != null) {
                if (!getCondition().getValue().contains(vehicle.getType())) error = true;
            } else error = true;

            if (error) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlayerMounts getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListEntityTypeFeature(this,  new ArrayList<>(), FeatureSettingsSCore.ifPlayerMounts, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfPlayerMounts getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMounts(parent);
    }
}
