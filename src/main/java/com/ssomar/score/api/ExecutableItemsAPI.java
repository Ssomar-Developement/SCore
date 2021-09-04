package com.ssomar.score.api;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.executableitems.items.ExecutableItemBuilder;
import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;

public class ExecutableItemsAPI {
	
	/* Verify if id is a valid ExecutableItem ID */
	public static boolean isValidID(String id) {
		return ItemManager.getInstance().containsLoadedItemWithID(id);
	}
	
	public static ItemStack getExecutableItem(String id) {
		if(isValidID(id)) {
			return ItemManager.getInstance().getLoadedItemWithID(id).formItem(1, null);
		}
		else return null;
	}
	
	public static ItemStack getExecutableItem(String id, int amount) {
		if(isValidID(id)) {
			return ItemManager.getInstance().getLoadedItemWithID(id).formItem(amount, null);
		}
		else return null;
	}
	
	public static ItemStack getExecutableItem(String id, int amount, Player creator) {
		if(isValidID(id)) {
			return ItemManager.getInstance().getLoadedItemWithID(id).formItem(amount, creator);
		}
		else return null;
	}
	
	public static ItemStack getExecutableItemWithUsage(String id, int usage) {
		if(isValidID(id)) {
			return ItemManager.getInstance().getLoadedItemWithID(id).formItem(1, null, usage);
		}
		else return null;
	}
	
	public static ItemStack getExecutableItemWithUsage(String id, int amount, int usage) {
		if(isValidID(id)) {
			return ItemManager.getInstance().getLoadedItemWithID(id).formItem(amount, null, usage);
		}
		else return null;
	}
	
	public static ItemStack getExecutableItemWithUsage(String id, int amount, int usage, Player creator) {
		if(isValidID(id)) {
			return ItemManager.getInstance().getLoadedItemWithID(id).formItem(amount, creator, usage);
		}
		else return null;
	}
	
	/* To place at the end of your itemBuilder , it adds infos for for item to be recognized as an ExecutableItem */
	public static ItemStack addExecutableItemInfos(ItemStack item, String EI_ID, @Nullable Player creator) {
		return ExecutableItemBuilder.builderForOraxen(item, EI_ID, creator);
	}
	
	public static boolean isExecutableItem(ItemStack itemStack) {
		return ItemManager.getInstance().getExecutableItem(itemStack) != null;
	}
	
	@Nullable
	public static Item getExecutableItemConfig(ItemStack itemStack) {
		return ItemManager.getInstance().getExecutableItem(itemStack);
	}
	
	@Nullable
	public static Item getExecutableItemConfig(String id) {
		return ItemManager.getInstance().getLoadedItemWithID(id);
	}

}
