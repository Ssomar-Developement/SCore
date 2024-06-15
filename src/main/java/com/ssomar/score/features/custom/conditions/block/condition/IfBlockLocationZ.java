package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;

public class IfBlockLocationZ extends BlockConditionFeature<NumberConditionFeature, IfBlockLocationZ> {

    public IfBlockLocationZ(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifBlockLocationZ);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), request.getBlock().getLocation().getZ())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public String[] getEditorDescription() {
        String[] finalDescription = new String[super.getEditorDescription().length + 1];
        if (getCondition().getValue().isPresent()) {
            finalDescription[finalDescription.length - 1] = "&7Condition: &e" + getCondition().getValue().get();
        } else {
            finalDescription[finalDescription.length - 1] = "&7Condition: &cNO CONDITION";
        }
        return finalDescription;
    }

    @Override
    public IfBlockLocationZ getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifBlockLocationZ));
    }

    @Override
    public IfBlockLocationZ getNewInstance(FeatureParentInterface parent) {
        return new IfBlockLocationZ(parent);
    }
}
