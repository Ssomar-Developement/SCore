package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfEntityHealth extends EntityConditionFeature<NumberConditionFeature, IfEntityHealth> {


    public IfEntityHealth(FeatureParentInterface parent) {
        super(parent, "ifEntityHealth", "If entity health", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if(hasCondition() && entity instanceof LivingEntity) {
            LivingEntity lE = (LivingEntity) entity;
            if(!StringCalculation.calculation(getCondition().getValue().get(), lE.getHealth())) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }

        return true;
    }

    @Override
    public IfEntityHealth getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), "ifEntityHealth", "If entity health", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfEntityHealth getNewInstance() {
        return new IfEntityHealth(getParent());
    }
}
