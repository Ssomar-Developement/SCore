package com.ssomar.score.features.custom.foodFeatures;

import com.ssomar.score.features.FeatureForItemArgs;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FoodFeaturesPaper {


    public void applyOnItem(@NotNull FeatureForItemArgs args, boolean isMeat) {
        ItemStack item = args.getItem();
        if (!item.hasData(DataComponentTypes.CONSUMABLE) && isMeat)
            item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().animation(ItemUseAnimation.EAT));
    }

}
