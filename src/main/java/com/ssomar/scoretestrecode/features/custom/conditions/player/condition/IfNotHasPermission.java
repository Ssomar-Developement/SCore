package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListUncoloredStringFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotHasPermission extends PlayerConditionFeature<ListUncoloredStringFeature, IfNotHasPermission> {

    public IfNotHasPermission(FeatureParentInterface parent) {
        super(parent, "ifNotHasPermission", "If not has permission", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            for (String perm : getCondition().getValue()) {
                if (player.hasPermission(perm)) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfNotHasPermission getValue() {
        return  this;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), "ifNotHasPermission", new ArrayList<>(), "If has not permission", new String[]{}, Material.ANVIL, false, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotHasPermission getNewInstance() {
        return new IfNotHasPermission(getParent());
    }
}
