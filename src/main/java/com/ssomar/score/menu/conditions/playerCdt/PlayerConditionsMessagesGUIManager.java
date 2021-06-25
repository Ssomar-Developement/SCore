package com.ssomar.score.menu.conditions.playerCdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class PlayerConditionsMessagesGUIManager extends GUIManager<PlayerConditionsMessagesGUI>{

	private static PlayerConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions pC, String detail) {
		cache.put(p, new PlayerConditionsMessagesGUI(sPlugin, sObject, sActivator, pC, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				//String plName = sPlugin.getNameDesign();

				if(name.contains("Reset")) {
					cache.replace(p, new PlayerConditionsMessagesGUI(sPlugin, sObject, sAct, new PlayerConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					savePlayerConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sObject.getActivator(sAct.getID()));
				}

				else if(name.contains("Exit")) {
					p.closeInventory();
				}

				else if(name.contains("Back")) {
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct);
				}
				
				else if(!name.isEmpty()) {

					if(name.contains(PlayerConditionsMessagesGUI.IF_FLYING_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_FLYING_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_FLYING_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_GLIDING_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_GLIDING_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_GLIDING_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_HAS_PERMISSION_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_HAS_PERMISSION_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_HAS_PERMISSION_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_IN_BIOME_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_IN_BIOME_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_IN_BIOME_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_IN_REGION_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_IN_REGION_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_IN_REGION_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_IN_WORLD_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_IN_WORLD_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_IN_WORLD_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_LIGHT_LEVEL_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_LIGHT_LEVEL_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_LIGHT_LEVEL_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_NOT_HAS_PERMISSION_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_NOT_HAS_PERMISSION_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_NOT_HAS_PERMISSION_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_NOT_IN_BIOME_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_NOT_IN_BIOME_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_NOT_IN_BIOME_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_NOT_IN_REGION_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_NOT_IN_REGION_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_NOT_IN_REGION_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_NOT_IN_WORLD_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_NOT_IN_WORLD_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_NOT_IN_WORLD_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_NOT_SNEAKING_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_NOT_SNEAKING_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_NOT_SNEAKING_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_NOT_TARGET_BLOCK_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_NOT_TARGET_BLOCK_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_NOT_TARGET_BLOCK_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_PLAYER_EXP_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_PLAYER_EXP_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_PLAYER_EXP_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_PLAYER_FOOD_LEVEL_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_PLAYER_FOOD_LEVEL_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_PLAYER_FOOD_LEVEL_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_PLAYER_HEALTH_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_PLAYER_HEALTH_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_PLAYER_HEALTH_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_PLAYER_LEVEL_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_PLAYER_LEVEL_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_PLAYER_LEVEL_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_SNEAKING_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_SNEAKING_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_SNEAKING_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_SWIMMING_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_SWIMMING_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_SWIMMING_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_TARGET_BLOCK_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_TARGET_BLOCK_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_TARGET_BLOCK_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_POS_X_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_POS_X_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_POS_X_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_POS_Y_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_POS_Y_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_POS_Y_MSG));
					}
					
					else if(name.contains(PlayerConditionsMessagesGUI.IF_POS_Z_MSG)) {
						requestWriting.put(p, PlayerConditionsMessagesGUI.IF_POS_Z_MSG);
						this.sendRequestMessage(p, cache.get(p).getActuallyWithColor(PlayerConditionsMessagesGUI.IF_POS_Z_MSG));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendRequestMessage(Player p, String actualMsg) {
		p.closeInventory();
		space(p);

		TextComponent message = new TextComponent(
				StringConverter.coloredString("&a&l"+ExecutableBlocks.NAME_2+" &aEnter a new message or &aedit &athe &amessage: "));

		TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
		edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(actualMsg)));
		edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current message")).create()));

		TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
		newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new message here.."));
		newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(StringConverter.coloredString("&aClick here to set new message")).create()));

		TextComponent noMsg = new TextComponent(StringConverter.coloredString("&c&l[NO MSG]"));
		noMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "NO MESSAGE"));
		noMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(StringConverter.coloredString("&cClick here to set no msg")).create()));

		
		message.addExtra(new TextComponent(" "));
		message.addExtra(edit);
		message.addExtra(new TextComponent(" "));
		message.addExtra(newName);
		message.addExtra(new TextComponent(" "));
		message.addExtra(noMsg);

		p.spigot().sendMessage(message);
		space(p);
	}
	
	public void shiftClicked(Player p, ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				//String plName = sPlugin.getNameDesign();

				if(name.contains("Reset")) {
					cache.replace(p, new PlayerConditionsMessagesGUI(sPlugin, sObject, sAct, new PlayerConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					savePlayerConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sObject.getActivator(sAct.getID()));
				}

				else if(name.contains("Exit")) {
					p.closeInventory();
				}

				else if(name.contains("Back")) {
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct);
				}
				else {
					String detail = cache.get(p).getDetail();
					savePlayerConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					if(detail.contains("owner")) PlayerConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getOwnerConditions(), detail);
					else if(detail.contains("target")) PlayerConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getTargetPlayerConditions(), detail);
					else if(detail.contains("player")) PlayerConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getPlayerConditions(), detail);
				}
			}
		}
	}

	public void receivedMessage(Player p, String message) {
		//SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		String request = requestWriting.get(p);

		if(message .contains("NO MESSAGE")) cache.get(p).updateMessage(request, "");
		else cache.get(p).updateMessage(request, message);
		requestWriting.remove(p);
		cache.get(p).openGUISync(p);
	}

	public void savePlayerConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		PlayerConditions pC = cache.get(p).getConditions();

		pC.setIfFlyingMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_FLYING_MSG));
		pC.setIfGlidingMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_GLIDING_MSG));
		pC.setIfHasPermissionMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_HAS_PERMISSION_MSG));
		pC.setIfInBiomeMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_IN_BIOME_MSG));
		pC.setIfInRegionMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_IN_REGION_MSG));
		pC.setIfInWorldMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_IN_WORLD_MSG));
		pC.setIfLightLevelMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_LIGHT_LEVEL_MSG));
		pC.setIfNotHasPermissionMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_NOT_HAS_PERMISSION_MSG));
		pC.setIfNotInBiomeMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_NOT_IN_BIOME_MSG));
		pC.setIfNotInRegionMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_NOT_IN_REGION_MSG));
		pC.setIfNotInWorldMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_NOT_IN_WORLD_MSG));
		pC.setIfNotSneakingMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_NOT_SNEAKING_MSG));
		pC.setIfNotTargetBlockMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_NOT_TARGET_BLOCK_MSG));
		pC.setIfPlayerEXPMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_PLAYER_EXP_MSG));
		pC.setIfPlayerFoodLevelMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_PLAYER_FOOD_LEVEL_MSG));
		pC.setIfPlayerHealthMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_PLAYER_HEALTH_MSG));
		pC.setIfPlayerLevelMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_PLAYER_LEVEL_MSG));
		pC.setIfSneakingMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_SNEAKING_MSG));
		pC.setIfSwimmingMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_SWIMMING_MSG));
		pC.setIfTargetBlockMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_TARGET_BLOCK_MSG));
		pC.setIfPosXMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_POS_X_MSG));
		pC.setIfPosYMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_POS_Y_MSG));
		pC.setIfPosZMsg(cache.get(p).getMessage(PlayerConditionsMessagesGUI.IF_POS_Z_MSG));
		
		PlayerConditions.savePlayerConditions(sPlugin, sObject, sActivator, pC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static PlayerConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new PlayerConditionsMessagesGUIManager();
		return instance;
	}
}
