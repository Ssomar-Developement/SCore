package com.ssomar.score.features.custom.conditions.world.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.world.WorldConditionFeature;
import com.ssomar.score.features.types.list.ListWeatherFeature;
import com.ssomar.score.utils.messages.SendMessage;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfWeather extends WorldConditionFeature<ListWeatherFeature, IfWeather> {

    public IfWeather(FeatureParentInterface parent) {
        super(parent, "ifWeather", "If weather", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(World world, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            String currentW = "";
            if (world.isThundering()) currentW = "STORM";

            else if (world.hasStorm()) currentW = "RAIN";
            else currentW = "CLEAR";

            if (!getCondition().getValue().contains(currentW)) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
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
        setCondition(new ListWeatherFeature(this, "ifWeather", new ArrayList<>(), "If weather", new String[]{"&7The whitelisted weathers"}, Material.ANVIL, false, true));
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
