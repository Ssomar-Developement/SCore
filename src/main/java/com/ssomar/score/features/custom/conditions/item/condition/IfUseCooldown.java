package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IfUseCooldown extends ItemConditionFeature<NumberConditionFeature, IfUseCooldown> {


    public IfUseCooldown(FeatureParentInterface parent) {
        super(parent,  FeatureSettingsSCore.ifUseCooldown);
    }

    @Override
    public boolean verifCondition(ItemConditionRequest request) {

        if (hasCondition()) {
            SsomarDev.testMsg("IfUseCooldown has condition", true);
            Optional<Player> playerOpt = request.getPlayerOpt();
            ItemStack itemStack = request.getItemStack();
            if (playerOpt.isPresent()){
                Player player = playerOpt.get();
                int cooldown = player.getCooldown(itemStack);

                SsomarDev.testMsg("currentCooldown: "+cooldown, true);
                if (!StringCalculation.calculation(getCondition().getValue(playerOpt, request.getSp()).get(), cooldown)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfUseCooldown getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, FeatureSettingsSCore.ifUseCooldown));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfUseCooldown getNewInstance(FeatureParentInterface parent) {
        return new IfUseCooldown(parent);
    }
}
