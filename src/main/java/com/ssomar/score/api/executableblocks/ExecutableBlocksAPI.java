package com.ssomar.score.api.executableblocks;

import com.ssomar.executableblocks.blocks.ExecutableBlockManager;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.api.executableblocks.config.ExecutableBlocksManagerInterface;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlocksPlacedManagerInterface;

public class ExecutableBlocksAPI {

	/** Get the ExecutableBlocks Manager,
	 * It allow you to get / retrieve the ExecutableBlocks Placed **/
	public static ExecutableBlocksManagerInterface getExecutableBlocksManager() {
		return ExecutableBlockManager.getInstance();

	}

	/** Get the ExecutableBlocksPlaced Manager,
	 * It allow you to get / retrieve the ExecutableBlocks Placed **/
	public static ExecutableBlocksPlacedManagerInterface getExecutableBlocksPlacedManager() {
		return ExecutableBlockPlacedManager.getInstance();
	}
}
