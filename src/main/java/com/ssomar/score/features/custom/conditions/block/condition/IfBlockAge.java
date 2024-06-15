package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter
@Setter
public class IfBlockAge extends BlockConditionFeature<NumberConditionFeature, IfBlockAge> {


    public IfBlockAge(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifBlockAge);
    }

    @Override
    public IfBlockAge getValue() {
        return this;
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
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifBlockAge));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfBlockAge getNewInstance(FeatureParentInterface parent) {
        return new IfBlockAge(parent);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        Optional<Player> playerOpt = request.getPlayerOpt();
        Block b = request.getBlock();
        if (b.getState().getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) b.getState().getBlockData();
            int age = ageable.getAge();
            if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(playerOpt, request.getSp()).get(), age)) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }
}
