package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.PlotSquaredAPI;
import org.bukkit.entity.Player;

public class IfPlayerMustBeOnHisPlot extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeOnHisPlot> {

    public IfPlayerMustBeOnHisPlot(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlayerMustBeOnHisPlot);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (SCore.hasPlotSquared) {
            Player player = request.getPlayer();
            if (getCondition().getValue(request.getSp())) {
                if (!PlotSquaredAPI.playerIsInHisPlot(player, player.getLocation())) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeOnHisPlot getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifPlayerMustBeOnHisPlot, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfPlayerMustBeOnHisPlot getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeOnHisPlot(parent);
    }
}
