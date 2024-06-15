package com.ssomar.score.features.custom.conditions.custom.condition.confirmation;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionFeature;
import com.ssomar.score.features.custom.conditions.custom.CustomConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.entity.Player;

import java.util.Map;

public class IfNeedPlayerConfirmation extends CustomConditionFeature<BooleanFeature, IfNeedPlayerConfirmation> {

    public IfNeedPlayerConfirmation(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifNeedPlayerConfirmation);
    }

    @Override
    public boolean verifCondition(CustomConditionRequest request) {
        Player p = request.getLauncher();
        StringPlaceholder sp = request.getSp();
        String id = sp.getId() + "-" + sp.getActivator_id();

        if (getCondition().getValue(request.getSp())) {
            boolean needConfirmation = true;
            Map<Player, String> needConfirmationMap = ConfirmationManager.getInstance().getNeedConfirm();
            if (needConfirmationMap.containsKey(p) && id.equals(needConfirmationMap.get(p))) {
                needConfirmation = false;
                ConfirmationManager.getInstance().getNeedConfirm().remove(p);
            }
            if (needConfirmation) {
                ConfirmationManager.getInstance().getNeedConfirm().put(p, id);
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNeedPlayerConfirmation getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifNeedPlayerConfirmation, true));
        getCancelEventIfError().setDefaultValue(true);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfNeedPlayerConfirmation getNewInstance(FeatureParentInterface parent) {
        return new IfNeedPlayerConfirmation(parent);
    }

}
