package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
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

public class IfUsage extends ItemConditionFeature<NumberConditionFeature, IfUsage> {


    public IfUsage(FeatureParentInterface parent) {
        super(parent, "ifUsage", "If usage", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        ExecutableItemObject executableItem = new ExecutableItemObject(itemStack);
        if (executableItem.isValid()) {
            executableItem.loadExecutableItemInfos();
            if (hasCondition()) {
                if (!StringCalculation.calculation(getCondition().getValue(playerOpt, messageSender.getSp()).get(), executableItem.getUsage())) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfUsage getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, "ifUsage", "If usage", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfUsage getNewInstance(FeatureParentInterface parent) {
        return new IfUsage(parent);
    }
}
