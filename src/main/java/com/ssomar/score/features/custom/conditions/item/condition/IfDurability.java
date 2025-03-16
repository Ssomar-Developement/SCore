package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.custom.conditions.item.ItemConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

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
            int maxDurability = itemStack.getType().getMaxDurability();
            if (itemStack.hasItemMeta()){
                ItemMeta meta = itemStack.getItemMeta();
                if(meta instanceof Damageable){
                    Damageable damageable = (Damageable) meta;
                    if (SCore.is1v20v5Plus() && damageable.hasMaxDamage()) maxDurability = damageable.getMaxDamage();
                }
            }

            if (!StringCalculation.calculation(getCondition().getValue(playerOpt, request.getSp()).get(), maxDurability - itemStack.getDurability())) {
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
