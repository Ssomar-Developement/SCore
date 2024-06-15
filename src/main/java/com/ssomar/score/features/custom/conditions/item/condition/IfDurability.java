package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IfDurability extends ItemConditionFeature<NumberConditionFeature, IfDurability> {


    public IfDurability(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifDurability);
    }

    @Override
    public boolean verifCondition(ItemConditionRequest request) {

        if (hasCondition()) {
            Optional<Player> playerOpt = request.getPlayerOpt();
            ItemStack itemStack = request.getItemStack();
            if (!StringCalculation.calculation(getCondition().getValue(playerOpt, request.getSp()).get(), itemStack.getType().getMaxDurability() - itemStack.getDurability())) {
                runInvalidCondition(request);
                return false;
            }
        }

        return true;
    }

    @Override
    public IfDurability getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifDurability));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfDurability getNewInstance(FeatureParentInterface parent) {
        return new IfDurability(parent);
    }
}
