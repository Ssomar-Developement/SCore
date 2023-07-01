package com.ssomar.score.features.custom.conditions.customei.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.customei.CustomEIConditionFeature;
import com.ssomar.score.features.custom.conditions.customei.CustomEIConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;

public class IfNeedPlayerConfirmation extends CustomEIConditionFeature<BooleanFeature, IfNeedPlayerConfirmation> {

    public IfNeedPlayerConfirmation(FeatureParentInterface parent) {
        super(parent, "ifNeedPlayerConfirmation", "If need player confirmation", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public IfNeedPlayerConfirmation getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNeedPlayerConfirmation", false, "If need player confirmation", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNeedPlayerConfirmation getNewInstance(FeatureParentInterface parent) {
        return new IfNeedPlayerConfirmation(parent);
    }

    @Override
    public boolean verifCondition(CustomEIConditionRequest request){
        return true;
    }

}
