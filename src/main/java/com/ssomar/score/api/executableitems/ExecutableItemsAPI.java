package com.ssomar.score.api.executableitems;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.executableitems.executableitems.manager.ExecutableItemsManager;
import com.ssomar.score.api.executableitems.config.ExecutableItemObjectInterface;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import org.bukkit.inventory.ItemStack;

public class ExecutableItemsAPI {

    /**
     * Get the ExecutableItems Manager,
     * It allow you to get / retrieve the ExecutableBlocks Placed
     **/

    public static ExecutableItemsManagerInterface getExecutableItemsManager() {
       return (ExecutableItemsManagerInterface) ExecutableItemsManager.getInstance();
    }

    public static ExecutableItemObjectInterface getExecutableItemObject(ItemStack itemStack) {
        return new ExecutableItemObject(itemStack);
    }

}
