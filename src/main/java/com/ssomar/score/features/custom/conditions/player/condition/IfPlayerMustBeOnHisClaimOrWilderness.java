package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.*;
import org.bukkit.entity.Player;

public class IfPlayerMustBeOnHisClaimOrWilderness extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeOnHisClaimOrWilderness> {

    public IfPlayerMustBeOnHisClaimOrWilderness(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifPlayerMustBeOnHisClaimOrWilderness);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Player player = request.getPlayer();
            if (SCore.hasLands) {
                LandsIntegrationAPI lands = new LandsIntegrationAPI(SCore.plugin);
                if (!lands.playerIsInHisClaim(player, player.getLocation(), true)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
            if (SCore.hasFactionsUUID) {
                FactionsUUIDAPI lands = new FactionsUUIDAPI();
                if (!lands.playerIsInHisClaim(player.getUniqueId(), player.getLocation(), true)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
            if (SCore.hasGriefPrevention) {
                if (!GriefPreventionAPI.playerIsInHisClaim(player, player.getLocation(), true)) {
                    runInvalidCondition(request);
                    return false;
                }
            }

            if (SCore.hasGriefDefender) {
                if (!GriefDefenderAPI.playerIsInHisClaim(player, player.getLocation(), true)) {
                    runInvalidCondition(request);
                    return false;
                }
            }

            if (SCore.hasResidence) {
                if (!ResidenceAPI.playerIsInHisClaim(player, player.getLocation(), true)) {
                    runInvalidCondition(request);
                    return false;
                }
            }

            if(SCore.hasProtectionStones){
                if(!ProtectionStonesAPI.playerIsInHisClaim(player, player.getLocation(), true)){
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeOnHisClaimOrWilderness getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifPlayerMustBeOnHisClaimOrWilderness, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfPlayerMustBeOnHisClaimOrWilderness getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeOnHisClaimOrWilderness(parent);
    }
}
