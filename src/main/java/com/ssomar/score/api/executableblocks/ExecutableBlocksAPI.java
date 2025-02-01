package com.ssomar.score.api.executableblocks;


import com.ssomar.executableblocks.executableblocks.ExecutableBlockObject;
import com.ssomar.executableblocks.executableblocks.ExecutableBlocksManager;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlocksPlacedManager;
import com.ssomar.score.api.executableblocks.config.ExecutableBlockObjectInterface;
import com.ssomar.score.api.executableblocks.config.ExecutableBlocksManagerInterface;
import org.bukkit.inventory.ItemStack;

public class ExecutableBlocksAPI {

    /**
     * Get the ExecutableBlocks Manager,
     * It allows you to get / retrieve the ExecutableBlocks Configurations
     **/
    public static ExecutableBlocksManagerInterface getExecutableBlocksManager() {
        return ExecutableBlocksManager.getInstance();
    }

    /**
     * Get the ExecutableBlocksPlaced Manager,
     * It allows you to get / retrieve the ExecutableBlocks Placed
     **/
    public static ExecutableBlocksPlacedManager getExecutableBlocksPlacedManager() {
        return ExecutableBlocksPlacedManager.getInstance();
    }


    /**
     * Get the ExecutableBlockObject
     * It allows you to get / retrieve the ExecutableBlocks Configurations under its item form
     **/
    public static ExecutableBlockObjectInterface getExecutableBlockObject(ItemStack itemStack) {
        return new ExecutableBlockObject(itemStack);
    }
}
