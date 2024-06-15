package com.ssomar.score.features.custom.conditions.world.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.world.WorldConditionFeature;
import com.ssomar.score.features.custom.conditions.world.WorldConditionRequest;
import com.ssomar.score.features.types.list.ListWeatherFeature;
import org.bukkit.World;

import java.util.ArrayList;

public class IfWeather extends WorldConditionFeature<ListWeatherFeature, IfWeather> {

    public IfWeather(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifWeather);
    }

    @Override
    public boolean verifCondition(WorldConditionRequest request) {
        World world = request.getWorld();
        if (hasCondition()) {
            String currentW = "";
            if (world.isThundering()) currentW = "STORM";

            else if (world.hasStorm()) currentW = "RAIN";
            else currentW = "CLEAR";

            if (!getCondition().getValue().contains(currentW)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfWeather getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListWeatherFeature(this,  new ArrayList<>(), FeatureSettingsSCore.ifWeather, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfWeather getNewInstance(FeatureParentInterface parent) {
        return new IfWeather(parent);
    }
}
