package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListUncoloredStringFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfHasPermission extends PlayerConditionFeature<ListUncoloredStringFeature, IfHasPermission> {


    public IfHasPermission(FeatureParentInterface parent) {
        super(parent, "ifHasPermission", "If has permission", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            boolean valid = true;
            for (String perm : getCondition().getValue()) {
                if (!player.hasPermission(perm)) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfHasPermission getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), "ifHasPermission", new ArrayList<>(), "If has permission", new String[]{}, Material.ANVIL, false, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfHasPermission getNewInstance() {
        return new IfHasPermission(getParent());
    }
}
