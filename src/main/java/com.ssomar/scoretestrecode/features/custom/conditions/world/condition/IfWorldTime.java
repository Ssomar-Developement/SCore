package com.ssomar.scoretestrecode.features.custom.conditions.world.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.world.WorldConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfWorldTime extends WorldConditionFeature<NumberConditionFeature, IfWorldTime> {


    public IfWorldTime(FeatureParentInterface parent) {
        super(parent, "ifWorldTime", "If world time", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(World world, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(playerOpt, messageSender.getSp()).get(), world.getTime())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfWorldTime getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, "ifWorldTime", "If world time", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfWorldTime getNewInstance(FeatureParentInterface parent) {
        return new IfWorldTime(parent);
    }
}
