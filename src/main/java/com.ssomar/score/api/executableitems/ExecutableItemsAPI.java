package com.ssomar.score.api.executableitems;

import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import com.ssomar.testRecode.executableItems.manager.NewExecutableItemsManager;

public class ExecutableItemsAPI {

    /**
     * Get the ExecutableItems Manager,
     * It allow you to get / retrieve the ExecutableBlocks Placed
     **/
    public static ExecutableItemsManagerInterface getExecutableItemsManager() {
        return (ExecutableItemsManagerInterface) NewExecutableItemsManager.getInstance();
    }

}
