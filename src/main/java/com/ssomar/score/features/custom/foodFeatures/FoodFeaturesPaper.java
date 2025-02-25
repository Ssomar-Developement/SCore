package com.ssomar.score.features.custom.foodFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureForItemArgs;
import com.ssomar.score.utils.logging.Utils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FoodFeaturesPaper {


    public void applyOnItem(@NotNull FeatureForItemArgs args, boolean isMeat) {
        ItemStack item = args.getItem();
        SsomarDev.testMsg("FoodFeaturesPaper applyOnItem", true);
        if (!item.hasData(DataComponentTypes.CONSUMABLE) && isMeat) {
            SsomarDev.testMsg("FoodFeaturesPaper applyOnItem2", true);
            try {
                item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().animation(ItemUseAnimation.EAT));
            } catch (Exception e) {
                Utils.sendConsoleMsg(SCore.plugin, "&cError while applying the food features on an item");
                e.printStackTrace();
            }
        }
    }

}
