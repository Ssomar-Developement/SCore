package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.usedapi.TownyToolAPI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlayerMustBeInHisTown extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeInHisTown> {

    public IfPlayerMustBeInHisTown(FeatureParentInterface parent) {
        super(parent, "ifPlayerMustBeInHisTown", "If player must be in his town", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            if (SCore.hasTowny) {
                if (!TownyToolAPI.playerIsInHisTown(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeInHisTown getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlayerMustBeInHisTown", false, "If player must be in his town", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfPlayerMustBeInHisTown getNewInstance() {
        return new IfPlayerMustBeInHisTown(getParent());
    }
}
