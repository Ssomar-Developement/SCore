package com.ssomar.score.api.executableitems;

import com.ssomar.executableitems.executableitems.ExecutableItemsManager;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import com.ssomar.score.api.executableitems.config.NewExecutableItemsManagerInterface;

public class ExecutableItemsAPI {

	/** Get the ExecutableItems Manager,
	 * It allow you to get / retrieve the ExecutableBlocks Placed **/
	public static ExecutableItemsManagerInterface getExecutableItemsManager() {
		return ExecutableItemsManager.getInstance();
	}

}
