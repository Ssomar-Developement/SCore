package com.ssomar.score.api;

import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.executableblocks.blocks.ExecutableBlockManager;
import com.ssomar.executableblocks.blocks.ExecutableBlocksBuilder;
import com.ssomar.executableitems.items.ItemManager;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ExecutableBlocksAPI {

	/* Verify if id is a valid ExecutableBlock ID*/
	public static boolean isValidID(String id) {
		return ItemManager.getInstance().containsLoadedItemWithID(id);
	}
	
	public static ItemStack getExecutableBlock(String id) {
		if(isValidID(id)) {
			return ExecutableBlockManager.getInstance().getLoadedBlockWithID(id).formItem(1, null);
		}
		else return null;
	}
	
	/* To place at the end of your itemBuilder , it adds infos for item to be recognized as an ExecutableBlock */
	public static ItemStack addExecutableBlockInfos(ItemStack item, String EB_ID, @Nullable Player creator) {
		if(!isValidID(EB_ID)) return item;
		return ExecutableBlocksBuilder.builderForOraxen(item, EB_ID, creator);
	}
	
}
