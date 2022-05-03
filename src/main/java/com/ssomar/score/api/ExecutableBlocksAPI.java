package com.ssomar.score.api;

import com.ssomar.executableblocks.blocks.ExecutableBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.executableblocks.blocks.ExecutableBlockManager;
import com.ssomar.executableblocks.blocks.ExecutableBlocksBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Deprecated
public class ExecutableBlocksAPI {

	/* Verify if id is a valid ExecutableBlock ID*/
	public static boolean isValidID(String id) {
		return ExecutableBlockManager.getInstance().getLoadedObjectWithID(id).isPresent();
	}

	public static ItemStack getExecutableBlock(String id) {
		Optional<ExecutableBlock> oOpt = ExecutableBlockManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			// TOOO return oOpt.get().buildItem(1, Optional.empty());
			return null;
		}
		else return null;
	}
	
	/* To place at the end of your itemBuilder , it adds infos for item to be recognized as an ExecutableBlock */
	public static ItemStack addExecutableBlockInfos(ItemStack item, String EB_ID, @Nullable Player creator) {
		if(!isValidID(EB_ID)) return item;
		return ExecutableBlocksBuilder.builderForOraxen(item, EB_ID, creator);
	}
	
}
