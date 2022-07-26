package com.ssomar.score.features.custom.conditions.item.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.item.ItemConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class IfCrossbowMustBeCharged extends ItemConditionFeature<BooleanFeature, IfCrossbowMustBeCharged> {


    public IfCrossbowMustBeCharged(FeatureParentInterface parent) {
        super(parent, "ifCrossbowMustBeCharged", "If crossbow must be charged", new String[]{}, Material.CROSSBOW, false);
    }

    @Override
    public boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if (hasCondition() && itemStack.getType().toString().contains("CROSSBOW")) {

            ItemMeta itemMeta = null;
            boolean hasItemMeta = itemStack.hasItemMeta();
            if (hasItemMeta) itemMeta = itemStack.getItemMeta();

            if (hasItemMeta && itemMeta instanceof CrossbowMeta) {
                CrossbowMeta cMeta = (CrossbowMeta) itemMeta;
                boolean charged = cMeta.hasChargedProjectiles();

                if (!charged) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfCrossbowMustBeCharged getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, "ifCrossbowMustBeCharged", false, "If crossbow must be charged", new String[]{}, Material.CROSSBOW, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfCrossbowMustBeCharged getNewInstance(FeatureParentInterface parent) {
        return new IfCrossbowMustBeCharged(parent);
    }
}
