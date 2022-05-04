package com.ssomar.score.api;

import com.ssomar.executableblocks.blocks.ExecutableBlock;
import com.ssomar.executableblocks.blocks.ExecutableBlockManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Deprecated
public class ExecutableBlocksAPI {

	/* Verify if id is a valid ExecutableBlock ID*/
	@Deprecated
	public static boolean isValidID(String id) {
		return ExecutableBlockManager.getInstance().getLoadedObjectWithID(id).isPresent();
	}

	@Deprecated
	public static ItemStack getExecutableBlock(String id) {
		Optional<ExecutableBlock> oOpt = ExecutableBlockManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().buildItem(1, Optional.empty());
		}
		else return null;
	}
}
