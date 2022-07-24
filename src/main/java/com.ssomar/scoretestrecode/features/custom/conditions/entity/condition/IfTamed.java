package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfTamed extends EntityConditionFeature<BooleanFeature, IfTamed> {

    public IfTamed(FeatureParentInterface parent) {
        super(parent, "ifTamed", "If tamed", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && entity instanceof Tameable && !((Tameable) entity).isTamed()) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }

        return true;
    }

    @Override
    public IfTamed getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifTamed", false, "If tamed", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfTamed getNewInstance(FeatureParentInterface parent) {
        return new IfTamed(parent);
    }
}
