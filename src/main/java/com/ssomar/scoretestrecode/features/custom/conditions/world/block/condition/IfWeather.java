package com.ssomar.scoretestrecode.features.custom.conditions.world.block.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.world.WorldCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.world.block.WorldConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListUncoloredStringFeature;
import com.ssomar.scoretestrecode.features.types.ListWeatherFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfWeather extends WorldConditionFeature<ListWeatherFeature, IfWeather> {

    public IfWeather(FeatureParentInterface parent) {
        super(parent, "ifWeather", "If weather", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(World world, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if(hasCondition()) {
            String currentW = "";
            if(world.isThundering()) currentW = "STORM";

            else if(world.hasStorm()) currentW = "RAIN";
            else currentW = "CLEAR";

            if(!getCondition().getValue().contains(currentW)) {
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
        setCondition(new ListWeatherFeature(this, "ifWeather", new ArrayList<>(), "If weather", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfWeather getNewInstance() {
        return new IfWeather(getParent());
    }
}
