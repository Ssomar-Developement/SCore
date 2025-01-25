package com.ssomar.score.api.executableitems;

import com.ssomar.executableitems.executableitems.ExecutableItem;
import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.executableitems.executableitems.ItemStackToExecutableItemConverter;
import com.ssomar.executableitems.executableitems.manager.ExecutableItemsManager;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.api.executableitems.config.ExecutableItemObjectInterface;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import org.bukkit.inventory.ItemStack;

public class ExecutableItemsAPI {

    /**
     * Get the ExecutableItems Manager,
     * It allows you to get / retrieve the ExecutableBlocks Placed
     **/

    public static ExecutableItemsManagerInterface getExecutableItemsManager() {
       return (ExecutableItemsManagerInterface) ExecutableItemsManager.getInstance();
    }

    public static ExecutableItemObjectInterface getExecutableItemObject(ItemStack itemStack) {
        return new ExecutableItemObject(itemStack);
    }

    /**
     * Register a new ExecutableItemObject
     * @param itemStack the itemStack to register
     * @param id the id of the ExecutableItem
     * @param folder the folder where the ExecutableItem will be saved (ex: "custom/")
     * @return the ExecutableItemInterface object
     */
    public static ExecutableItemInterface registerNewExecutableItemObject(ItemStack itemStack, String id, String folder) {
        ExecutableItem converter = ItemStackToExecutableItemConverter.convert(itemStack);
        converter.setId(id);
        converter.setPath("plugins/ExecutableItems/items/"+folder +id + ".yml");
        converter.save();
        ExecutableItemsManager.getInstance().addLoadedObject(converter);
        return converter;
    }

}
