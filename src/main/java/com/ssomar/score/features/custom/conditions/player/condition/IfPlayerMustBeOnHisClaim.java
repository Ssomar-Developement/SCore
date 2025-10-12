package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.*;
import org.bukkit.entity.Player;

public class IfPlayerMustBeOnHisClaim extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeOnHisClaim> {

    public IfPlayerMustBeOnHisClaim(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerMustBeOnHisClaim);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Player player = request.getPlayer();
            if (SCore.hasLands) {
                LandsIntegrationAPI lands = new LandsIntegrationAPI(SCore.plugin);
                if (!lands.playerIsInHisClaim(player, player.getLocation(), false)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
            if (SCore.hasFactionsUUID) {
                FactionsUUIDAPI factionsUUIDAPI = new FactionsUUIDAPI();
                if (!factionsUUIDAPI.playerIsInHisClaim(player.getUniqueId(), player.getLocation(), false)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
            if (SCore.hasGriefPrevention) {
                if (!GriefPreventionAPI.playerIsInHisClaim(player, player.getLocation(), false)) {
                    runInvalidCondition(request);
                    return false;
                }
            }

            if (SCore.hasGriefDefender) {
                if (!GriefDefenderAPI.playerIsInHisClaim(player, player.getLocation(), false)) {
                    runInvalidCondition(request);
                    return false;
                }
            }

            if (SCore.hasResidence) {
                if (!ResidenceAPI.playerIsInHisClaim(player, player.getLocation(), false)) {
                    runInvalidCondition(request);
                    return false;
                }
            }

            if(SCore.hasProtectionStones){
                if(!ProtectionStonesAPI.playerIsInHisClaim(player, player.getLocation(), false)){
                    runInvalidCondition(request);
                    return false;
                }
            }

            if(SCore.hasExcellentClaims){
                if(!ExcellentClaimsAPI.playerIsInHisClaim(player, player.getLocation(), false)){
                    runInvalidCondition(request);
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
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifPlayerMustBeOnHisClaim));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfPlayerMustBeOnHisClaim getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeOnHisClaim(parent);
    }
}
