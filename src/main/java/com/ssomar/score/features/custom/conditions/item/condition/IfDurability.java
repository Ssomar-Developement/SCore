package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IfDurability extends ItemConditionFeature<NumberConditionFeature, IfDurability> {


    public IfDurability(FeatureParentInterface parent) {
        super(parent, "ifDurability", "If durability", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if (hasCondition()) {
            if (!StringCalculation.calculation(getCondition().getValue(playerOpt, messageSender.getSp()).get(), itemStack.getType().getMaxDurability() - itemStack.getDurability())) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
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
        setCondition(new NumberConditionFeature(this, "ifDurability", "If durability", new String[]{}, Material.ANVIL, false));
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
