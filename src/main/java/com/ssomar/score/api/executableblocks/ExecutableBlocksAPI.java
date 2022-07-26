package com.ssomar.score.api.executableblocks;

import com.ssomar.executableblocks.executableblocks.manager.ExecutableBlocksManager;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.api.executableblocks.config.ExecutableBlocksManagerInterface;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlocksPlacedManagerInterface;

public class ExecutableBlocksAPI {

    /**
     * Get the ExecutableBlocks Manager,
     * It allow you to get / retrieve the ExecutableBlocks Placed
     **/
    public static ExecutableBlocksManagerInterface getExecutableBlocksManager() {
        return ExecutableBlocksManager.getInstance();
    }

    /**
     * Get the ExecutableBlocksPlaced Manager,
     * It allow you to get / retrieve the ExecutableBlocks Placed
     **/
    public static ExecutableBlocksPlacedManagerInterface getExecutableBlocksPlacedManager() {
       return ExecutableBlockPlacedManager.getInstance();
    }
}
