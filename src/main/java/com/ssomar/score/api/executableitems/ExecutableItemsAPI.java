package com.ssomar.score.api.executableitems;

import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.api.executableblocks.config.ExecutableBlocksManagerInterface;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExecutableItemsAPI {

	/** Get the ExecutableItems Manager,
	 * It allow you to get / retrieve the ExecutableBlocks Placed **/
	public static ExecutableItemsManagerInterface getExecutableItemsManager() {
		// TODO return ExecutableItemManager.getInstance();
		return null;
	}

}
