package com.ssomar.score.menu.conditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.SCore;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class CustomConditionsGUIManager extends GUIManager<CustomConditionsGUI>{
	
	private static CustomConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, CustomConditions cC, String detail) {
		cache.put(p, new CustomConditionsGUI(sPlugin, sObject, sAct, cC, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item!=null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				String plName = sPlugin.getNameDesign();

				if(name.contains(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION)) cache.get(p).changeBoolean(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION);
				
				if(name.contains(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_ISLAND)) {
					if(SCore.hasIridiumSkyblock) cache.get(p).changeBoolean(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION);
					else p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &cYou haven't a compatible skyblock plugin to change this option ! (IridiumSkyblock"));
				}

				else if(name.contains("Reset")) {
					p.closeInventory();
					cache.replace(p, new CustomConditionsGUI(sPlugin, sObject, sAct, new CustomConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					p.closeInventory();
					saveCustomConditionsEI(p);
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

	/*public void receivedMessage(Player p, String message) {
		
	}*/	

	public void saveCustomConditionsEI(Player p) {	
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		CustomConditions cC = new CustomConditions();

		cC.setIfNeedPlayerConfirmation(cache.get(p).getBoolean(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION));
		cC.setIfPlayerMustBeOnHisIsland(cache.get(p).getBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_ISLAND));

		CustomConditions.saveCustomConditions(sPlugin, sObject, sActivator, cC);
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}

	public static CustomConditionsGUIManager getInstance() {
		if(instance == null) instance = new CustomConditionsGUIManager();
		return instance;
	}
}
