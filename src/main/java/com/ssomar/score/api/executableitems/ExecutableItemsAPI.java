package com.ssomar.score.api.executableitems;

import com.ssomar.executableitems.executableitems.manager.ExecutableItemsManager;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;

public class ExecutableItemsAPI {

    /**
     * Get the ExecutableItems Manager,
     * It allow you to get / retrieve the ExecutableBlocks Placed
     **/
    public static ExecutableItemsManagerInterface getExecutableItemsManager() {
        return (ExecutableItemsManagerInterface) ExecutableItemsManager.getInstance();
    }

}
