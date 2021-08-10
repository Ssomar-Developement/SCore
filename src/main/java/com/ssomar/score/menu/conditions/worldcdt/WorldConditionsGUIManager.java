package com.ssomar.score.menu.conditions.worldcdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;


public class WorldConditionsGUIManager extends GUIManagerConditions<WorldConditionsGUI>{

	private static WorldConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions wC, String detail) {
		cache.put(p, new WorldConditionsGUI(sPlugin, sObject, sActivator, wC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		if(i.name.contains(WorldConditionsGUI.IF_WEATHER)) {
			requestWriting.put(i.player, WorldConditionsGUI.IF_WEATHER);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfWeather());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION IF WEATHER:"));
			this.showIfWeatherEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(WorldConditionsGUI.IF_WORLD_TIME)) {
			requestWriting.put(i.player, WorldConditionsGUI.IF_WORLD_TIME);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION IF WORLD TIME:"));

			this.showCalculationGUI(i.player, "World time", cache.get(i.player).getIfWorlTime());
			space(i.player);
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		WorldConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getWorldConditions(), detail);
	
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<WorldConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<WorldConditionsGUI> interact) {
		// TODO Auto-generated method stub
		return false;
	}


	public void receivedMessage(Player p, String message) {
		boolean notExit = true;
		String prepareMsg = message.trim();
		if(prepareMsg.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(prepareMsg).equals("exit with delete")) {
				if(requestWriting.get(p).equals(WorldConditionsGUI.IF_WORLD_TIME)) {
					cache.get(p).updateIfWorldTime("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				pass = true;
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
				notExit = false;
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


	public static WorldConditionsGUIManager getInstance() {
		if(instance == null) instance = new WorldConditionsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		WorldConditions wC = (WorldConditions)cache.get(p).getConditions();

		wC.setIfWeather(cache.get(p).getIfWeather());
		wC.setIfWorldTime(cache.get(p).getIfWorlTime());

		WorldConditions.saveWorldConditions(sPlugin, sObject, sActivator, wC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}
}

