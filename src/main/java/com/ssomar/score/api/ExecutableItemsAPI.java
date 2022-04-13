package com.ssomar.score.api;

import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.sobject.SObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ExecutableItemsAPI {


	public static boolean isValidID(String id) {
		return ItemManager.getInstance().getLoadedObjectWithID(id).isPresent();
	}

	@Nullable
	public static ItemStack getExecutableItem(String id) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(1, null);
		}
		else return null;
	}

	/**
		It adds all ExecutableItems settings in the itemStack, but it doesnt modify the material and the customModelDataTag.
	 **/
	@Nullable
	public static ItemStack addExecutableItemSettingsWithoutTexturesThings(String id, @Nullable Player owner, @Nullable ItemStack itemStack) {
		Optional<Item> oOpt = ItemManager.getInstance().getLoadedObjectWithID(id);
		if(oOpt.isPresent()) {
			return oOpt.get().formItem(1, owner, itemStack);
		}
		else return itemStack;
	}

	public static Optional<String> getExecutableItemId(ItemStack itemStack) {
		ExecutableItem ei = new ExecutableItem(itemStack);
		if(ei.isValid()){
			return Optional.ofNullable(ei.getConfig().getId());
		}
		else return Optional.ofNullable(null);
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
		ExecutableItem ei = new ExecutableItem(itemStack);
		return ei.isValid();
	}

	public static boolean isExecutableItem(ItemStack itemStack, String id) {
		ExecutableItem ei = new ExecutableItem(itemStack);
		if(ei.isValid()){
			return ei.getConfig().getId().equals(id);
		}
		else return false;
	}

	@Nullable
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

	public static List<String> getExecutableItemIdsList() {
		return ItemManager.getInstance().getLoadedObjectsIDs();
	}
}
