package com.ssomar.score.sobject.sactivator.menu.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.SCore;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;


public class PlayerConditionsGUIManager extends GUIManager<PlayerConditionsGUI>{

	private static PlayerConditionsGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions pC, String detail) {
		cache.put(p, new PlayerConditionsGUI(sPlugin, sObject, sActivator, pC, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				String plName = sPlugin.getNameDesign();

				if(name.contains(PlayerConditionsGUI.IF_SNEAKING)) cache.get(p).changeBoolean(PlayerConditionsGUI.IF_SNEAKING);

				else if(name.contains(PlayerConditionsGUI.IF_NOT_SNEAKING)) cache.get(p).changeBoolean(PlayerConditionsGUI.IF_NOT_SNEAKING);

				else if(name.contains(PlayerConditionsGUI.IF_SWIMMING)) cache.get(p).changeBoolean(PlayerConditionsGUI.IF_SWIMMING);

				else if(name.contains(PlayerConditionsGUI.IF_GLIDING)) cache.get(p).changeBoolean(PlayerConditionsGUI.IF_GLIDING);

				else if(name.contains(PlayerConditionsGUI.IF_FLYING)) cache.get(p).changeBoolean(PlayerConditionsGUI.IF_FLYING);

				else if(name.contains(PlayerConditionsGUI.IF_IN_WORLD)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_IN_WORLD);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfInWorld());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF IN WORLD:"));
					this.showIfInWorldEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_NOT_IN_WORLD)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_NOT_IN_WORLD);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfNotInWorld());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF IN NOT WORLD:"));
					this.showIfNotInWorldEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_IN_BIOME)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_IN_BIOME);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfInBiome());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF IN BIOME:"));
					this.showIfInBiomeEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_NOT_IN_BIOME)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_NOT_IN_BIOME);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfNotInBiome());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF IN NOT BIOME:"));
					this.showIfNotInBiomeEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_IN_REGION)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_IN_REGION);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfInRegion());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF IN REGION:"));
					this.showIfInRegionEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_NOT_IN_REGION)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_NOT_IN_REGION);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfNotInRegion());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF NOT IN REGION:"));
					this.showIfNotInRegionEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_HAS_PERMISSION)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_HAS_PERMISSION);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfHasPermission());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF HAS PERMISSION:"));
					this.showIfHasPermissionEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_NOT_HAS_PERMISSION)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_NOT_HAS_PERMISSION);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfNotHasPermission());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF NOT HAS PERMISSION:"));
					this.showIfNotHasPermissionEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_TARGET_BLOCK)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_TARGET_BLOCK);
					if(!currentWriting.containsKey(p)) {
						List<String> convert = new ArrayList<>();
						for(Material mat : cache.get(p).getIfTargetBlock()) {
							convert.add(mat.toString());
						}
						currentWriting.put(p, convert);
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF TARGET BLOCK:"));
					this.showIfTargetBlockEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_NOT_TARGET_BLOCK)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_NOT_TARGET_BLOCK);
					if(!currentWriting.containsKey(p)) {	
						List<String> convert = new ArrayList<>();
						for(Material mat : cache.get(p).getIfNotTargetBlock()) {
							convert.add(mat.toString());
						}
						currentWriting.put(p, convert);
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF NOT TARGET BLOCK:"));
					this.showIfNotTargetBlockEditor(p);
					space(p);
				}

				else if(name.contains(PlayerConditionsGUI.IF_PLAYER_HEALTH)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_PLAYER_HEALTH);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF PLAYER HEALTH:"));

					this.showCalculationGUI(p, "Health", cache.get(p).getIfPlayerHealth());
					space(p);
				}
				
				else if(name.contains(PlayerConditionsGUI.IF_LIGHT_LEVEL)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_LIGHT_LEVEL);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF LIGHT LEVEL:"));

					this.showCalculationGUI(p, "Light level", cache.get(p).getIfLightLevel());
					space(p);
				}
				
				else if(name.contains(PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF PLAYER FOOD LEVEL:"));

					this.showCalculationGUI(p, "Food level", cache.get(p).getIfPlayerFoodLevel());
					space(p);
				}
				else if(name.contains(PlayerConditionsGUI.IF_PLAYER_EXP)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_PLAYER_EXP);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF PLAYER EXP:"));

					this.showCalculationGUI(p, "EXP", cache.get(p).getIfPlayerEXP());
					space(p);
				}
				else if(name.contains(PlayerConditionsGUI.IF_PLAYER_LEVEL)) {
					requestWriting.put(p, PlayerConditionsGUI.IF_PLAYER_LEVEL);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF PLAYER LEVEL:"));

					this.showCalculationGUI(p, "Level", cache.get(p).getIfPlayerLevel());
					space(p);
				}	

				else if(name.contains("Reset")) {
					p.closeInventory();
					cache.replace(p, new PlayerConditionsGUI(sPlugin, sObject, sAct, new PlayerConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					p.closeInventory();
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
			}
		}
	}

	public void receivedMessage(Player p, String message) {
		boolean notExit= true;
		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass=false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {
				if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_HEALTH)) {
					cache.get(p).updateIfPlayerHealth("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_LIGHT_LEVEL)) {
					cache.get(p).updateIfLightLevel("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL)) {
					cache.get(p).updateIfPlayerFoodLevel("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_EXP)) {
					cache.get(p).updateIfPlayerEXP("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_LEVEL)) {
					cache.get(p).updateIfPlayerLevel("");
				}
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				pass=true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_WORLD)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfInWorld(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_WORLD)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfNotInWorld(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_BIOME)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfInBiome(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_BIOME)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfNotInBiome(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_REGION)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfInRegion(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_REGION)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfNotInRegion(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_PERMISSION)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfHasPermission(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_TARGET_BLOCK)) {
					List<Material> result= new ArrayList<>();
					try {
						for(String str : currentWriting.get(p)) {
							result.add(Material.valueOf(str));
						}
					}catch(Exception e) {}
					cache.get(p).updateIfTargetBlock(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_TARGET_BLOCK)) {
					List<Material> result= new ArrayList<>();
					try {
						for(String str : currentWriting.get(p)) {
							result.add(Material.valueOf(str));
						}
					}catch(Exception e) {}
					cache.get(p).updateIfNotTargetBlock(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_HAS_PERMISSION)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfNotHasPermission(result);
				}
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit=false;
			}
		}
		if(notExit) {
			if(message.contains("delete line <")) {	
				this.deleteLine(message, p);
				this.showTheGoodEditor(requestWriting.get(p), p);
				space(p);
				space(p);			
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_WORLD) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_WORLD)) {
				if(Bukkit.getServer().getWorld(message)!=null) {
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message);
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new world!"));
				}
				else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid world please !"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}
			
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_BIOME) ||  requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_BIOME)) {

				if(currentWriting.containsKey(p)) {
					currentWriting.get(p).add(message);
				}
				else {
					ArrayList<String> list = new ArrayList<>();
					list.add(message);
					currentWriting.put(p, list);
				}
				p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new biome!"));

				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_REGION) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_REGION)) {
				if(SCore.is1v12()) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lError the conditions ifInRegion and ifNotInRegion are not available in 1.12 due to a changement of worldguard API "));
				}else {
					if(message.isEmpty() || message.equals(" ")) {
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid region please !"));
					}
					else {
						if(currentWriting.containsKey(p)) {
							currentWriting.get(p).add(message);
						}
						else {
							ArrayList<String> list = new ArrayList<>();
							list.add(message);
							currentWriting.put(p, list);
						}
						p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new region!"));
					}
					this.showTheGoodEditor(requestWriting.get(p), p);
				}
			}
			
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_PERMISSION) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_HAS_PERMISSION)) {
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid permission please !"));
				}
				else {
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message);
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new permission!"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_TARGET_BLOCK) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_TARGET_BLOCK)) {
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid block please !"));
				}
				else {
					try {
						Material.valueOf(message.toUpperCase());
					}catch(Exception e) {
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid block please !"));
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&l> https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html !"));
						return;
					}
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message.toUpperCase());
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new target block!"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}
			
			
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_HEALTH)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerHealth(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for health please !"));
					this.showCalculationGUI(p, "Health", cache.get(p).getIfPlayerHealth());
				}
			}
			
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_LIGHT_LEVEL)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfLightLevel(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for light level please !"));
					this.showCalculationGUI(p, "Light level", cache.get(p).getIfLightLevel());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerFoodLevel(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for food level please !"));
					this.showCalculationGUI(p, "Food level", cache.get(p).getIfPlayerFoodLevel());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_EXP)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerEXP(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for EXP please !"));
					this.showCalculationGUI(p, "EXP", cache.get(p).getIfPlayerEXP());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_LEVEL)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerLevel(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for level please !"));
					this.showCalculationGUI(p, "Level", cache.get(p).getIfPlayerLevel());
				}
			}
		}

	}

	public void showIfInWorldEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifInWorld:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "World:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotInWorldEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotInWorld:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not world:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfInBiomeEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifInBiome:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Biome:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotInBiomeEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotInBiome:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not biome:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotInRegionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotInRegion:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not region", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfInRegionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifInRegion:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Region", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotHasPermissionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotHasPermission:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not has permission", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfHasPermissionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifHasPermission:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Has permission", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}
	
	public void showIfTargetBlockEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifTargetBlock:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Target block", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}
	
	public void showIfNotTargetBlockEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotTargetBlock:");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not target block", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}
	
	public void showTheGoodEditor(String value, Player p) {

		switch(value) {

		case PlayerConditionsGUI.IF_NOT_TARGET_BLOCK:
			showIfNotTargetBlockEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_TARGET_BLOCK:
			showIfTargetBlockEditor(p);
			break;
		
		case PlayerConditionsGUI.IF_HAS_PERMISSION:
			showIfHasPermissionEditor(p);
			break;
		
		case PlayerConditionsGUI.IF_NOT_HAS_PERMISSION:
			showIfNotHasPermissionEditor(p);
			break;
		
		case PlayerConditionsGUI.IF_IN_REGION:
			showIfInRegionEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_NOT_IN_REGION:
			showIfNotInRegionEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_NOT_IN_BIOME:
			showIfNotInBiomeEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_IN_BIOME:
			showIfInBiomeEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_IN_WORLD:
			showIfInWorldEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_NOT_IN_WORLD:
			showIfNotInWorldEditor(p);
			break;
		
		default:
			break;
		}
	}

	public void savePlayerConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		PlayerConditions pC = new PlayerConditions();

		pC.setIfSneaking(cache.get(p).getBoolean(PlayerConditionsGUI.IF_SNEAKING));
		pC.setIfNotSneaking(cache.get(p).getBoolean(PlayerConditionsGUI.IF_NOT_SNEAKING));
		pC.setIfSwimming(cache.get(p).getBoolean(PlayerConditionsGUI.IF_SWIMMING));
		pC.setIfGliding(cache.get(p).getBoolean(PlayerConditionsGUI.IF_GLIDING));
		pC.setIfFlying(cache.get(p).getBoolean(PlayerConditionsGUI.IF_FLYING));
		pC.setIfInWorld(cache.get(p).getIfInWorld());
		pC.setIfNotInWorld(cache.get(p).getIfNotInWorld());
		pC.setIfInBiome(cache.get(p).getIfInBiome());
		pC.setIfNotInBiome(cache.get(p).getIfNotInBiome());
		pC.setIfInRegion(cache.get(p).getIfInRegion());
		pC.setIfNotInRegion(cache.get(p).getIfNotInRegion());
		pC.setIfHasPermission(cache.get(p).getIfHasPermission());
		pC.setIfNotHasPermission(cache.get(p).getIfNotHasPermission());
		pC.setIfPlayerHealth(cache.get(p).getIfPlayerHealth());
		pC.setIfLightLevel(cache.get(p).getIfLightLevel());
		pC.setIfPlayerFoodLevel(cache.get(p).getIfPlayerFoodLevel());
		pC.setIfPlayerEXP(cache.get(p).getIfPlayerEXP());
		pC.setIfPlayerLevel(cache.get(p).getIfPlayerLevel());
		pC.setIfTargetBlock(cache.get(p).getIfTargetBlock());
		pC.setIfNotTargetBlock(cache.get(p).getIfNotTargetBlock());

		PlayerConditions.saveEntityConditions(sPlugin, sObject, sActivator, pC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static PlayerConditionsGUIManager getInstance() {
		if(instance == null) instance = new PlayerConditionsGUIManager();
		return instance;
	}
}
