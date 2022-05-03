package com.ssomar.score.api;

import com.ssomar.executableitems.executableitems.ExecutableItem;
import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.executableitems.executableitems.ExecutableItemsManager;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Deprecated
public class ExecutableItemsAPI {

	@Deprecated()
	public static boolean isValidID(String id) {
		return com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().isValidID(id);
	}

	@Nullable @Deprecated
	public static ItemStack getExecutableItem(String id) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().buildItem(1, Optional.empty());
		}
		else return null;
	}

	/**
		It adds all ExecutableItems settings in the itemStack, but it doesnt modify the material and the customModelDataTag.
	 **/
	@Nullable @Deprecated
	public static ItemStack addExecutableItemSettingsWithoutTexturesThings(String id, @Nullable Player owner, @Nullable ItemStack itemStack) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().addExecutableItemInfos(itemStack, Optional.ofNullable(owner));
		}
		else return itemStack;
	}

	@Deprecated
	public static Optional<String> getExecutableItemId(ItemStack itemStack) {
		ExecutableItemObject ei = new ExecutableItemObject(itemStack);
		if(ei.isValid()){
			return Optional.ofNullable(ei.getConfig().getId());
		}
		else return Optional.ofNullable(null);
	}

	@Deprecated
	public static ItemStack getExecutableItem(String id, int amount) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().buildItem(amount, Optional.empty());
		}
		else return null;
	}

	@Deprecated
	public static ItemStack getExecutableItem(String id, int amount, Player creator) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().buildItem(amount, Optional.ofNullable(creator));
		}
		else return null;
	}

	@Deprecated
	public static ItemStack getExecutableItemWithUsage(String id, int usage) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().buildItem(1, Optional.ofNullable(usage), Optional.empty());
		}
		else return null;
	}

	@Deprecated
	public static ItemStack getExecutableItemWithUsage(String id, int amount, int usage) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().buildItem(amount, Optional.ofNullable(usage), Optional.empty());
		}
		else return null;
	}

	@Deprecated
	public static ItemStack getExecutableItemWithUsage(String id, int amount, int usage, Player creator) {
		Optional<ExecutableItemInterface> eiOpt = com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
		if(eiOpt.isPresent()) {
			return eiOpt.get().buildItem(amount, Optional.ofNullable(usage), Optional.ofNullable(creator));
		}
		else return null;
	}

	@Deprecated
	public static boolean isExecutableItem(ItemStack itemStack) {
		ExecutableItemObject ei = new ExecutableItemObject(itemStack);
		return ei.isValid();
	}

	@Deprecated
	public static boolean isExecutableItem(ItemStack itemStack, String id) {
		ExecutableItemObject ei = new ExecutableItemObject(itemStack);
		if(ei.isValid()){
			return ei.getConfig().getId().equals(id);
		}
		else return false;
	}

	@Deprecated
	public static List<String> getExecutableItemIdsList() {
		List<String> list = new ArrayList<>();
		for(ExecutableItem item : ExecutableItemsManager.getInstance().getLoadedObjects()) {
			list.add(item.getId());
		}
		return list;
	}
}
