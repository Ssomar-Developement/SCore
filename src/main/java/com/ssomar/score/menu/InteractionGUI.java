package com.ssomar.score.menu;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.EntityConditionsGUIManager;
import com.ssomar.score.menu.conditions.PlaceholdersConditionGUIManager;
import com.ssomar.score.menu.conditions.PlaceholdersConditionsGUIManager;
import com.ssomar.score.menu.conditions.PlayerConditionsGUIManager;
import com.ssomar.score.menu.conditions.WorldConditionsGUIManager;
import com.ssomar.score.utils.StringConverter;


public class InteractionGUI implements Listener{

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {

		if(!(e.getWhoClicked() instanceof Player)) return;	

		String title= e.getView().getTitle();
		Player player = (Player) e.getWhoClicked();
		try {
			if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
		}catch(NullPointerException error) {
			return;
		}

		int slot = e.getSlot();
		ItemStack itemS = e.getView().getItem(slot);
		//boolean notNullItem = itemS!=null;

		try {

			if(title.contains(StringConverter.coloredString("Editor - Conditions"))) {
				this.manage(player, itemS, title, "ConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Player Conditions"))) {
				this.manage(player, itemS, title, "PlayerConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - World Conditions"))) {
				this.manage(player, itemS, title, "WorldConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Entity Conditions"))) {
				this.manage(player, itemS, title, "EntityConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Placeholders Conditions"))) {
				this.manage(player, itemS, title, "PlaceholdersConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Plch condition"))) {
				this.manage(player, itemS, title, "PlaceholdersConditionGUIManager", e);
			}

		}catch (NullPointerException error) {
			error.printStackTrace();
		}
	}

	public void manage(Player player, ItemStack itemS, String title, String guiType, InventoryClickEvent e) {

		e.setCancelled(true);

		if(itemS==null) return;

		if(!itemS.hasItemMeta()) return;

		if(itemS.getItemMeta().getDisplayName().isEmpty()) return;

		//String itemName = itemS.getItemMeta().getDisplayName();

		boolean isShiftLeft = e.getClick().equals(ClickType.SHIFT_LEFT);

		switch (guiType) {
		case "ConditionsGUIManager":
			ConditionsGUIManager.getInstance().clicked(player, itemS);
			break;
			
		case "EntityConditionsGUIManager":
			EntityConditionsGUIManager.getInstance().clicked(player, itemS);
			break;
			
		case "WorldConditionsGUIManager":
			WorldConditionsGUIManager.getInstance().clicked(player, itemS);
			break;
			
		case "PlayerConditionsGUIManager":
			PlayerConditionsGUIManager.getInstance().clicked(player, itemS);
			break;
			
		case "PlaceholdersConditionsGUIManager":
			if(isShiftLeft) {
				PlaceholdersConditionsGUIManager.getInstance().shiftLeftClicked(player, itemS, title);
			}
			else PlaceholdersConditionsGUIManager.getInstance().clicked(player, itemS, title);
			break;
			
		case "PlaceholdersConditionGUIManager":
			PlaceholdersConditionGUIManager.getInstance().clicked(player, itemS);
			break;
			
		default:
			break;
		}
	}


	public String getActually(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		for(String s: lore) {
			if(StringConverter.decoloredString(s).contains("actually: ")) return StringConverter.decoloredString(s).split("actually: ")[1];
		}
		return null;
	}



	@EventHandler(priority = EventPriority.LOWEST)
	public void onChatEvent(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		if(PlayerConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlayerConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(EntityConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			EntityConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(WorldConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			WorldConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(PlaceholdersConditionGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlaceholdersConditionGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
	}	
}
