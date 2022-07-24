package com.ssomar.score.api.executableitems.config;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public interface ExecutableItemsManagerInterface {

    /**
     * Verify if id is a valid ExecutableItem ID
     *
     * @param id The ID to verify
     * @return true if it is a valid ID, false otherwise
     **/
    boolean isValidID(String id);

    /**
     * Get an ExecutableItem from its ID
     *
     * @param id The ID of the ExecutableItem
     * @return The ExecutableItem or an empty optional
     **/
    Optional<ExecutableItemInterface> getExecutableItem(String id);

    /**
     * Get an ExecutableItem from its itemStack form
     *
     * @param itemStack The itemStack to get the ExecutableItem from
     * @return The ExecutableItem or an empty optional
     **/
    Optional<ExecutableItemInterface> getExecutableItem(ItemStack itemStack);

    /**
     * Get all ExecutableItems Ids
     *
     * @return All ExecutableItems ids
     **/
    List<String> getExecutableItemIdsList();
}
