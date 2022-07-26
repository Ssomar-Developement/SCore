package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.usedapi.GriefDefenderAPI;
import com.ssomar.score.usedapi.GriefPreventionAPI;
import com.ssomar.score.usedapi.LandsIntegrationAPI;
import com.ssomar.score.usedapi.ResidenceAPI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlayerMustBeOnHisClaim extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeOnHisClaim> {

    public IfPlayerMustBeOnHisClaim(FeatureParentInterface parent) {
        super(parent, "ifPlayerMustBeOnHisClaim", "If player must be on his claim", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            if (SCore.hasLands) {
                LandsIntegrationAPI lands = new LandsIntegrationAPI(SCore.plugin);
                if (!lands.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
            if (SCore.hasGriefPrevention) {
                if (!GriefPreventionAPI.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }

            if (SCore.hasGriefDefender) {
                if (!GriefDefenderAPI.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }

            if (SCore.hasResidence) {
                if (!ResidenceAPI.playerIsInHisClaim(player, player.getLocation())) {
                    sendErrorMsg(playerOpt, messageSender);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeOnHisClaim getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlayerMustBeOnHisClaim", false, "If player must be on his claim", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfPlayerMustBeOnHisClaim getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeOnHisClaim(parent);
    }
}
