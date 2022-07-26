package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.types.list.ListColoredStringFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfName extends EntityConditionFeature<ListColoredStringFeature, IfName> {

    public IfName(FeatureParentInterface parent) {
        super(parent, "ifName", "If name", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            boolean notValid = true;
            for (String name : getCondition().getValue()) {
                if (StringConverter.decoloredString(entity.getName()).equalsIgnoreCase(name)) {
                    notValid = false;
                    break;
                }
            }
            if (notValid) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }

        return true;
    }

    @Override
    public IfName getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListColoredStringFeature(getParent(), "ifName", new ArrayList<>(), "If name", new String[]{}, Material.ANVIL, false, true, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfName getNewInstance(FeatureParentInterface parent) {
        return new IfName(parent);
    }
}
