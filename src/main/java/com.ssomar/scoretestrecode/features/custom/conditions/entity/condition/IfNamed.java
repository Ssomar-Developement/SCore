package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfNamed extends EntityConditionFeature<BooleanFeature, IfNamed> {


    public IfNamed(FeatureParentInterface parent) {
        super(parent, "ifNamed", "If name", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && entity instanceof Nameable && ((Nameable) entity).getCustomName() == null) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }

        return true;
    }

    @Override
    public IfNamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNamed", false, "If named", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNamed getNewInstance(FeatureParentInterface parent) {
        return new IfNamed(parent);
    }
}
