package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfFrozen extends EntityConditionFeature<BooleanFeature, IfFrozen> {

    public IfFrozen(FeatureParentInterface parent) {
        super(parent, "ifFrozen", "If frozen", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && SCore.is1v18Plus() && !entity.isFrozen()) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }

        return true;
    }

    @Override
    public IfFrozen getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifFrozen", false, "If frozen", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfFrozen getNewInstance(FeatureParentInterface parent) {
        return new IfFrozen(parent);
    }
}
