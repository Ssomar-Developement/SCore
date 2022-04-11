package com.ssomar.score.menu;

import com.ssomar.score.menu.activator.requiredei.RequiredEIGUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUIManager;
import com.ssomar.score.menu.commands.CommandsEditor;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import com.ssomar.score.menu.conditions.clean.NewConditionGUIManager;
import com.ssomar.score.menu.conditions.clean.NewConditionsGUI;
import com.ssomar.score.menu.conditions.customcdt.ei.CustomConditionsGUIManager;
import com.ssomar.score.menu.conditions.customcdt.ei.CustomConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.entitycdt.EntityConditionsGUIManager;
import com.ssomar.score.menu.conditions.entitycdt.EntityConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.itemcdt.ItemConditionsGUIManager;
import com.ssomar.score.menu.conditions.itemcdt.ItemConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.placeholdercdt.PlaceholdersConditionGUIManager;
import com.ssomar.score.menu.conditions.placeholdercdt.PlaceholdersConditionsGUIManager;
import com.ssomar.score.menu.conditions.playercdt.PlayerConditionsGUIManager;
import com.ssomar.score.menu.conditions.playercdt.PlayerConditionsMessagesGUIManager;
import com.ssomar.score.menu.particles.SParticleGUIManager;
import com.ssomar.score.menu.particles.SParticlesGUIManager;
import com.ssomar.score.projectiles.ProjectilesGUIManager;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class InteractionGUI implements Listener{

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {

		if(!(e.getWhoClicked() instanceof Player)) return;	

		String title = e.getView().getTitle();
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

			if(title.contains("Editor: Custom Projectiles")){
				this.manage(player, itemS, title, "ProjectilesEditor", e);
			}
			else if(title.contains(StringConverter.coloredString("Editor - Conditions"))) {
				this.manage(player, itemS, title, "ConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - RequiredEIs"))) {
				this.manage(player, itemS, title, "RequiredEIsGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - RequiredEI"))) {
				this.manage(player, itemS, title, "RequiredEIGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Particles"))) {
				this.manage(player, itemS, title, "SParticlesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Particle"))) {
				this.manage(player, itemS, title, "SParticleGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - Player Conditions Messages"))) {
				this.manage(player, itemS, title, "PlayerConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Player Conditions"))) {
				this.manage(player, itemS, title, "PlayerConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - World Conditions Messages"))) {
				this.manage(player, itemS, title, "WorldConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - World Conditions"))) {
				this.manage(player, itemS, title, "WorldConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Entity Conditions Messages"))) {
				this.manage(player, itemS, title, "EntityConditionsMessagesGUIManager", e);
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
			
			else if(title.contains(StringConverter.coloredString("Editor - Around block Conditions"))) {
				this.manage(player, itemS, title, "AroundBlockConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Around-Block condition"))) {
				this.manage(player, itemS, title, "AroundBlockConditionGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Item Conditions Messages"))) {
				this.manage(player, itemS, title, "ItemConditionMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Item Conditions"))) {
				this.manage(player, itemS, title, "ItemConditionGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - Block Conditions Messages"))) {
				this.manage(player, itemS, title, "BlockConditionMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Block Conditions"))) {
				this.manage(player, itemS, title, "BlockConditionGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Custom Conditions Messages"))) {
				this.manage(player, itemS, title, "CustomConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Custom Conditions"))) {
				this.manage(player, itemS, title, "CustomConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Custom projectiles"))) {
				this.manage(player, itemS, title, "ProjectilesGUIManager", e);
			}

		}catch (NullPointerException error) {
			error.printStackTrace();
		}
	}

	public void manage(Player player, ItemStack itemS, String title, String guiType, InventoryClickEvent e) {

		e.setCancelled(true);

		if(itemS == null) return;

		if(!itemS.hasItemMeta()) return;

		if(itemS.getItemMeta().getDisplayName().isEmpty()) return;

		//String itemName = itemS.getItemMeta().getDisplayName();

		boolean isShiftLeft = e.getClick().equals(ClickType.SHIFT_LEFT);

		if(e.getClickedInventory() instanceof NewConditionGUI){
			NewConditionGUIManager.getInstance().clicked(player, itemS, e.getClick());
		}
		
		switch (guiType) {
			case "ProjectilesEditor":
				GUI gui = new SimpleGUI(e.getClickedInventory());
				String id = gui.getActually(GUI.TITLE_COLOR+"&e>>&l &aProjectile ID:");
				//SsomarDev.testMsg("GUI ID: "+ id);
				for(SProjectiles proj : ProjectilesManager.getInstance().getProjectiles()){
					if(proj.getId().equals(id)){
						proj.sendInteractionConfigGUI(gui, player, itemS, title);
						break;
					}
				}
				break;
		case "ConditionsGUIManager":
			ConditionsGUIManager.getInstance().clicked(player, itemS);
			break;

		case "RequiredEIsGUIManager":
			if(isShiftLeft) {
				RequiredEIsGUIManager.getInstance().shiftLeftClicked(player, itemS, title);
			}
			else RequiredEIsGUIManager.getInstance().clicked(player, itemS, title);
			break;
			
		case "RequiredEIGUIManager":
			RequiredEIGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "SParticlesGUIManager":
				if(isShiftLeft) {
					SParticlesGUIManager.getInstance().shiftLeftClicked(player, itemS, title);
				}
				else SParticlesGUIManager.getInstance().clicked(player, itemS, title);
				break;

		case "SParticleGUIManager":
				SParticleGUIManager.getInstance().clicked(player, itemS, e.getClick());
				break;

		case "EntityConditionsGUIManager":
			EntityConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "EntityConditionsMessagesGUIManager":
			EntityConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "PlayerConditionsMessagesGUIManager":
			PlayerConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "PlayerConditionsGUIManager":
			PlayerConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "PlaceholdersConditionsGUIManager":
			PlaceholdersConditionsGUIManager.getInstance().clicked(player, itemS, title, e.getClick());
			break;

		case "PlaceholdersConditionGUIManager":
			PlaceholdersConditionGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;
			
		/*case "AroundBlockConditionsGUIManager":
			AroundBlockConditionsGUIManager.getInstance().clicked(player, itemS, title, e.getClick());
			break;*/

		/* case "AroundBlockConditionGUIManager":
			AroundBlockConditionGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;*/

		case "ItemConditionMessagesGUIManager":
			ItemConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "ItemConditionGUIManager":
			ItemConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "CustomConditionsMessagesGUIManager":
			CustomConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "CustomConditionsGUIManager":
			CustomConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "ProjectilesGUIManager":
			if(isShiftLeft) {
				ProjectilesGUIManager.getInstance().shiftLeftClicked(player, itemS, title);
			}
			else ProjectilesGUIManager.getInstance().clicked(player, itemS, title);

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
		if(CommandsEditor.getInstance().isAsking(p)){
			e.setCancelled(true);
			CommandsEditor.getInstance().receiveMessage(p, e.getMessage());
		} else if (NewConditionGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			NewConditionGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (PlayerConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlayerConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (PlayerConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlayerConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}  else if (EntityConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			EntityConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (EntityConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			EntityConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (PlaceholdersConditionGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlaceholdersConditionGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		/* else if(AroundBlockConditionGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			AroundBlockConditionGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}*/
		else if (ItemConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			ItemConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (ItemConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			ItemConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}  else if (CustomConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			CustomConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (CustomConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			CustomConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (RequiredEIGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			RequiredEIGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else if (SParticleGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			SParticleGUIManager.getInstance().receivedMessage(p, e.getMessage());
		} else {
			for (SProjectiles proj : ProjectilesManager.getInstance().getProjectiles()) {
				if (proj.hasRequestChat()) {
					e.setCancelled(true);
					proj.sendMessageForConfig(proj.getConfigGUI(), p, e.getMessage());
					break;
				}
			}
		}
	}	
}
