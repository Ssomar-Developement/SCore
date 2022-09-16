package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.ShopGUIPlusTool;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class IfContainerContainsSellableItem extends BlockConditionFeature<BooleanFeature, IfContainerContainsSellableItem> {

    public IfContainerContainsSellableItem(FeatureParentInterface parent) {
        super(parent, "ifContainerContainsSellableItem", "If container contains sellable item", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if (hasCondition()) {
            if(playerOpt.isPresent()) {
                if (b.getState() instanceof Container) {
                    Container container = (Container) b.getState();
                    Inventory inv = container.getInventory();
                    for (int i = 0; i < inv.getSize(); i++) {
                        ItemStack item = inv.getItem(i);
                        if (item != null && SCore.hasShopGUIPlus) {
                            double check = ShopGUIPlusTool.sellItem(playerOpt.get(), item);
                            if (check > 0) {
                                return true;
                            }
                        }
                    }
                }
            }

            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfContainerContainsSellableItem getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifContainerContainsSellableItem", false, "If container contains sellable item", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfContainerContainsSellableItem getNewInstance(FeatureParentInterface parent) {
        return new IfContainerContainsSellableItem(parent);
    }
}
