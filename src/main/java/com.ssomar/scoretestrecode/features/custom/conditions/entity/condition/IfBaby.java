package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfBaby extends EntityConditionFeature<BooleanFeature, IfBaby> {

    public IfBaby(FeatureParentInterface parent) {
        super(parent, "ifBaby", "If baby", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && entity instanceof Ageable && ((Ageable) entity).isAdult()) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }

        return true;
    }

    @Override
    public IfBaby getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifBaby", false, "If baby", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfBaby getNewInstance(FeatureParentInterface parent) {
        return new IfBaby(parent);
    }
}
