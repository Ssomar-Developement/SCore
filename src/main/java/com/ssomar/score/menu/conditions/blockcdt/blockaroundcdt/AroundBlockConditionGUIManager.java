package com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.AroundBlockCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class AroundBlockConditionGUIManager extends GUIManagerConditions<AroundBlockConditionGUI>{

	private static AroundBlockConditionGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<AroundBlockCondition> list, String detail) {
		cache.put(p, new AroundBlockConditionGUI(sPlugin, sObject, sActivator, list, detail));
		cache.get(p).openGUISync(p);
	}

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, AroundBlockCondition aBC, String detail) {
		cache.put(p, new AroundBlockConditionGUI(sPlugin, sObject, sActivator, aBC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> i) {
		if(i.name.contains(AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS)) {
			requestWriting.put(i.player, AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getMustBeExecutableBlock());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION MUST BE EXECUTABLEBLOCKS:"));
			this.showMustbeExecutableblocksEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(AroundBlockConditionGUI.MUST_BE_TYPE)) {
			requestWriting.put(i.player, AroundBlockConditionGUI.MUST_BE_TYPE);
			if(!currentWriting.containsKey(i.player)) {

				List<String> convert = new ArrayList<>();
				for(Material mat : cache.get(i.player).getMustBeType()) {
					convert.add(mat.toString());
				}
				currentWriting.put(i.player, convert);
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION TYPE MUST BE:"));
			this.showTypeMustBeEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains("Save") || i.name.contains("Create this Around block Condition")) {

			String detail = cache.get(i.player).getDetail();
			this.saveTheConfiguration(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			i.sActivator = i.sObject.getActivator(i.sActivator.getID());
			AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sActivator.getBlockConditions().getBlockAroundConditions(), detail);
		}

		else if(i.name.contains("Back")) {
			AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sActivator.getBlockConditions().getBlockAroundConditions(), cache.get(i.player).getDetail());
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
		return false;
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> i) {
		AroundBlockConditionGUI gui = cache.get(i.player);

		if(i.name.contains(AroundBlockConditionGUI.SOUTH_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.NORTH_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.WEST_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.EAST_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.ABOVE_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.UNDER_VALUE)) {

			gui.incrValue(StringConverter.decoloredString(i.name));
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> i) {
		if(i.name.contains(AroundBlockConditionGUI.SOUTH_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.NORTH_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.WEST_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.EAST_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.ABOVE_VALUE)
				|| i.name.contains(AroundBlockConditionGUI.UNDER_VALUE)) {

			cache.get(i.player).decrValue(i.name);
		}
		else return false;
		
		return true;
	}


	public void receivedMessage(Player p, String message) {
		boolean notExit = true;
		SPlugin sPlugin = cache.get(p).getsPlugin();
        String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				if(requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_TYPE)) {
					List<Material> result = new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						try {
							result.add(Material.valueOf(str));
						}
						catch(Exception e) {}
					}
					cache.get(p).updateMustBeType(result);
				}
				else if(requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS)) {
					List<String> result = new ArrayList<>();
					result.addAll(currentWriting.get(p));
					cache.get(p).updateMustBeExecutableBlock(result);
				}
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit = false;
			}
		}
		if(notExit) {
			if(message.contains("delete line <")) {	
				this.deleteLine(message, p);
				this.showTheGoodEditor(requestWriting.get(p), p);
				space(p);
				space(p);			
			}
			else if(requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_TYPE)) {
				try {
					Material mat = Material.valueOf(message.toUpperCase());
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(mat.toString());
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(mat.toString());
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new accepted type!"));
				}
				catch(Exception e) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid type/material please !"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS)) {
				if(currentWriting.containsKey(p)) {
					currentWriting.get(p).add(message);
				}
				else {
					ArrayList<String> list = new ArrayList<>();
					list.add(message);
					currentWriting.put(p, list);
				}
				p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new accepted EB!"));

				this.showTheGoodEditor(requestWriting.get(p), p);
			}
		}
	}

	public void showMustbeExecutableblocksEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ MustBeEB:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "EB:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showTypeMustBeEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ TypeMustBe:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Type:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showTheGoodEditor(String value, Player p) {

		switch(value) {

		case AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS:
			showMustbeExecutableblocksEditor(p);
			break;

		case AroundBlockConditionGUI.MUST_BE_TYPE:
			showTypeMustBeEditor(p);
			break;

		default:
			break;
		}
	}


	public static AroundBlockConditionGUIManager getInstance() {
		if(instance == null) instance = new AroundBlockConditionGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();

		String id = cache.get(p).getActually(AroundBlockConditionGUI.CDT_ID);
		AroundBlockCondition aBC = new AroundBlockCondition(id);

		aBC.setSouthValue(cache.get(p).getInt(AroundBlockConditionGUI.SOUTH_VALUE));
		aBC.setNorthValue(cache.get(p).getInt(AroundBlockConditionGUI.NORTH_VALUE));
		aBC.setWestValue(cache.get(p).getInt(AroundBlockConditionGUI.WEST_VALUE));
		aBC.setEastValue(cache.get(p).getInt(AroundBlockConditionGUI.EAST_VALUE));
		aBC.setAboveValue(cache.get(p).getInt(AroundBlockConditionGUI.ABOVE_VALUE));
		aBC.setUnderValue(cache.get(p).getInt(AroundBlockConditionGUI.UNDER_VALUE));

		aBC.setErrorMsg(cache.get(p).getActuallyWithColor(AroundBlockConditionGUI.ERROR_MSG));
		aBC.setBlockMustBeExecutableBlock(cache.get(p).getMustBeExecutableBlock());
		aBC.setBlockTypeMustBe(cache.get(p).getMustBeType());

		AroundBlockCondition.saveAroundBlockCdt(sPlugin, sObject, sActivator, aBC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}
}
