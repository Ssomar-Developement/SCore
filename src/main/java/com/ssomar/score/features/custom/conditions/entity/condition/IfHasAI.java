package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfHasAI extends EntityConditionFeature<BooleanFeature, IfHasAI> {

    public IfHasAI(FeatureParentInterface parent) {
        super(parent, "ifHasAI", "If has AI", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifHasAI", false, "If has AI", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfHasAI getNewInstance(FeatureParentInterface parent) {
        return new IfHasAI(parent);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && entity instanceof LivingEntity && !((LivingEntity) entity).hasAI()) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }

        return true;
    }

    @Override
    public IfHasAI getValue() {
        return this;
    }
}
