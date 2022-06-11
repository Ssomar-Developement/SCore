package com.ssomar.scoretestrecode.features.custom.conditions.item.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.item.ItemCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class IfCrossbowMustNotBeCharged extends ItemConditionFeature<BooleanFeature, IfCrossbowMustNotBeCharged> {

    public IfCrossbowMustNotBeCharged(FeatureParentInterface parent) {
        super(parent, "ifCrossbowMustNotBeCharged", "If crossbow must not be charged", new String[]{}, Material.CROSSBOW, false);
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if(hasCondition()  && itemStack.getType().toString().contains("CROSSBOW")){

            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if(hasItemMeta) itemMeta = itemStack.getItemMeta();

            if(hasItemMeta && itemMeta instanceof CrossbowMeta){
                CrossbowMeta cMeta = (CrossbowMeta)itemMeta;
                boolean charged = cMeta.hasChargedProjectiles();

                if(charged){
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfCrossbowMustNotBeCharged getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, "ifCrossbowMustNotBeCharged", false, "If crossbow must not be charged", new String[]{}, Material.CROSSBOW, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfCrossbowMustNotBeCharged getNewInstance() {
        return new IfCrossbowMustNotBeCharged(getParent());
    }
}
