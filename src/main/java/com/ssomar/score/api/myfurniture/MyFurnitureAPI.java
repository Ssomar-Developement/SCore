package com.ssomar.score.api.myfurniture;

import com.ssomar.executableitems.executableitems.manager.ExecutableItemsManager;
import com.ssomar.myfurniture.furniture.FurnitureObject;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import com.ssomar.score.api.myfurniture.config.FurnitureObjectInterface;
import org.bukkit.inventory.ItemStack;

public class MyFurnitureAPI {

    /**
     * Get the ExecutableItems Manager,
     * It allows you to get / retrieve the ExecutableBlocks Placed
     **/

    public static ExecutableItemsManagerInterface getExecutableItemsManager() {
       return (ExecutableItemsManagerInterface) ExecutableItemsManager.getInstance();
    }

    public static FurnitureObjectInterface getExecutableItemObject(ItemStack itemStack) {
        return new FurnitureObject(itemStack);
   }

}
