package com.ssomar.score.usedapi;

import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.modifier.PriceModifier;
import net.brcdev.shopgui.modifier.PriceModifierActionType;
import net.brcdev.shopgui.shop.item.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopGUIPlusTool {

    public static double sellItem(Player player, ItemStack item) {

        double amount = ShopGuiPlusApi.getItemStackPriceSell(player, item);
        if(amount == -1) {
            return -1;
        }

        try {
            ShopItem shopItem = ShopGuiPlusApi.getItemStackShopItem(item);

            PriceModifier priceModifier = ShopGuiPlusApi.getPriceModifier(player, shopItem, PriceModifierActionType.SELL);

            amount = amount * priceModifier.getModifier();
        }catch (Exception e) {}


        return amount;
    }
}
