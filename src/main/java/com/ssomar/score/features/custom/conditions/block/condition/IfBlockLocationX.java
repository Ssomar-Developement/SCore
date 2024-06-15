package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;

public class IfBlockLocationX extends BlockConditionFeature<NumberConditionFeature, IfBlockLocationX> {

    public IfBlockLocationX(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifBlockLocationX);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), request.getBlock().getLocation().getX())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
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
    public IfBlockLocationX getValue() {
        return this;
    }


    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifBlockLocationX));
    }

    @Override
    public IfBlockLocationX getNewInstance(FeatureParentInterface parent) {
        return new IfBlockLocationX(parent);
    }
}
