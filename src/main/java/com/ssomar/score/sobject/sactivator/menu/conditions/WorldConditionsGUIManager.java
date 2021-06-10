package com.ssomar.score.sobject.sactivator.menu.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;


public class WorldConditionsGUIManager extends GUIManager<WorldConditionsGUI>{

	private static WorldConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions wC, String detail) {
		cache.put(p, new WorldConditionsGUI(sPlugin, sObject, sActivator, wC, detail));
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

				if(name.contains(WorldConditionsGUI.IF_WEATHER)) {
					requestWriting.put(p, WorldConditionsGUI.IF_WEATHER);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfWeather());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION IF WEATHER:"));
					this.showIfWeatherEditor(p);
					space(p);
				}

				else if(name.contains(WorldConditionsGUI.IF_WORLD_TIME)) {
					requestWriting.put(p, WorldConditionsGUI.IF_WORLD_TIME);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION IF WORLD TIME:"));

					this.showCalculationGUI(p, "World time", cache.get(p).getIfWorlTime());
					space(p);
				}
				
				else if(name.contains("Reset")) {
					p.closeInventory();
					cache.replace(p, new WorldConditionsGUI(sPlugin, sObject, sAct, new WorldConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					p.closeInventory();
					saveWorldConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					new ConditionsGUI(sPlugin, sObject, sObject.getActivator(sAct.getID())).openGUISync(p);
				}

				else if(name.contains("Exit")) {
					p.closeInventory();
				}

				else if(name.contains("Back")) {
					p.closeInventory();
					new ConditionsGUI(sPlugin, sObject, sAct).openGUISync(p);
				}
			}
		}
	}

	public void receivedMessage(Player p, String message) {
		boolean notExit= true;
		String prepareMsg=  message.trim();
		if(prepareMsg.contains("exit")) {
			boolean pass=false;
			if(StringConverter.decoloredString(prepareMsg).equals("exit with delete")) {
				if(requestWriting.get(p).equals(WorldConditionsGUI.IF_WORLD_TIME)) {
					cache.get(p).updateIfWorldTime("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				pass=true;
			}
			if(StringConverter.decoloredString(prepareMsg).equals("exit") || pass) {
				if(requestWriting.get(p).equals(WorldConditionsGUI.IF_WEATHER)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfWeather(result);
				}
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit=false;
			}
		}
		if(notExit) {
			if(prepareMsg.contains("delete line <")) {	
				this.deleteLine(prepareMsg, p);
				if(requestWriting.get(p).equals(WorldConditionsGUI.IF_WEATHER)) this.showIfWeatherEditor(p);
				space(p);
				space(p);			
			}
			else if(requestWriting.get(p).equals(WorldConditionsGUI.IF_WEATHER)) {
				if(!prepareMsg.isEmpty()) {
					if(prepareMsg.equalsIgnoreCase("CLEAR") || prepareMsg.equalsIgnoreCase("STORM") || prepareMsg.equalsIgnoreCase("RAIN") ) {
						if(currentWriting.containsKey(p)) {
							currentWriting.get(p).add(prepareMsg);
						}
						else {
							ArrayList<String> list = new ArrayList<>();
							list.add(prepareMsg);
							currentWriting.put(p, list);
						}
						p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION &aYou have added new weather!"));
					}
					else {
						p.sendMessage(StringConverter.coloredString("&c&l[ExecutableItems] &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));
					}
				}
				else {
					p.sendMessage(StringConverter.coloredString("&c&l[ExecutableItems] &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));
				}
				this.showIfWeatherEditor(p);
			}
			else if(requestWriting.get(p).equals(WorldConditionsGUI.IF_WORLD_TIME)) {
				if(StringCalculation.isStringCalculation(prepareMsg)) {
					cache.get(p).updateIfWorldTime(prepareMsg);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l[ExecutableItems] &4&lERROR &cEnter a valid condition for world time please !"));
					this.showCalculationGUI(p, "World time", cache.get(p).getIfWorlTime());
				}
			}
		}

	}

	public void showIfWeatherEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifWeather: (RAIN, CLEAR, or STORM)");
		
		HashMap<String, String> suggestions = new HashMap<>();
		
		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Weather", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}


	public void saveWorldConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		WorldConditions wC = new WorldConditions();

		wC.setIfWeather(cache.get(p).getIfWeather());
		wC.setIfWorldTime(cache.get(p).getIfWorlTime());

		WorldConditions.saveEntityConditions(sPlugin, sObject, sActivator, wC);
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());

	}

	public static WorldConditionsGUIManager getInstance() {
		if(instance == null) instance = new WorldConditionsGUIManager();
		return instance;
	}
}

