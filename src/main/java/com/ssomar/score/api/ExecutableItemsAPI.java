package com.ssomar.score.api;

import com.ssomar.score.sobject.SObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.executableitems.items.ExecutableItemBuilder;
import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;

import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class ExecutableItemsAPI {


	public static boolean isValidID(String id) {
		return ItemManager.getInstance().getLoadedObjectWithID(id).isPresent();
	}

	public static ItemStack getExecutableItem(String id) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(1, null);
		}
		else return null;
	}

	public static ItemStack getExecutableItem(String id, int amount) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(amount, null);
		}
		else return null;
	}

	public static ItemStack getExecutableItem(String id, int amount, Player creator) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(amount, creator);
		}
		else return null;
	}

	public static ItemStack getExecutableItemWithUsage(String id, int usage) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(1, null, usage);
		}
		else return null;
	}

	public static ItemStack getExecutableItemWithUsage(String id, int amount, int usage) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(amount, null, usage);
		}
		else return null;
	}

	public static ItemStack getExecutableItemWithUsage(String id, int amount, int usage, Player creator) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(amount, creator, usage);
		}
		else return null;
	}

	public static boolean isExecutableItem(ItemStack itemStack) {
		return ItemManager.getInstance().getExecutableItem(itemStack) != null;
	}

	@javax.annotation.Nullable
	public static Item getExecutableItemConfig(ItemStack itemStack) {
		return ItemManager.getInstance().getExecutableItem(itemStack);
	}

	@Nullable
	public static Item getExecutableItemConfig(String id) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get();
		}
		else return null;
	}
}
