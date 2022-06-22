package com.ssomar.scoretestrecode.features.custom.conditions.item.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.item.ItemCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class IfDurability extends ItemConditionFeature<NumberConditionFeature, IfDurability> {


    public IfDurability(FeatureParentInterface parent) {
        super(parent, "ifDurability", "If durability", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if(hasCondition()) {
            if(!StringCalculation.calculation(getCondition().getValue().get(), itemStack.getType().getMaxDurability()-itemStack.getDurability())) {
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
        setCondition(new NumberConditionFeature(this, "ifDurability",  "If durability", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfDurability getNewInstance() {
        return new IfDurability(getParent());
    }
}
