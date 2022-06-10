package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.entity.EntityCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfInvulnerable extends EntityConditionFeature<BooleanFeature, IfInvulnerable> {

    public IfInvulnerable(FeatureParentInterface parent) {
        super(parent, "ifInvulnerable", "If invulnerable", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if(hasCondition() && !entity.isInvulnerable()) {
            sendErrorMsg(playerOpt, messageSender);
            return false;
        }
        return true;
    }

    @Override
    public IfInvulnerable getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifInvulnerable", false, "If invulnerable", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfInvulnerable getNewInstance() {
        return new IfInvulnerable(getParent());
    }
}
