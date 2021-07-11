package com.ssomar.score.menu.conditions.worldcdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.worldcdt.WorldConditionsMessagesGUI.WorldConditionsMessages;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class WorldConditionsMessagesGUIManager extends GUIManagerSCore<WorldConditionsMessagesGUI>{

	private static WorldConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions wC, String detail) {
		cache.put(p, new WorldConditionsMessagesGUI(sPlugin, sObject, sActivator, wC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public void clicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		if(i.name.contains("Save")) {
			saveWorldConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		
		else if(!i.name.isEmpty()) {
			for(WorldConditionsMessages wcMsg : WorldConditionsMessages.values()) {
				if(i.name.contains(wcMsg.name)) {
					requestWriting.put(i.player, wcMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(wcMsg.name);
					RequestMessage.sendRequestMessage(i.msgInfos);
				}
			}
		}
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
					cache.replace(p, new WorldConditionsMessagesGUI(sPlugin, sObject, sAct, new WorldConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					saveWorldConditionsEI(p);
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
					saveWorldConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					WorldConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getWorldConditions(), detail);
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

	public void saveWorldConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		WorldConditions wC = (WorldConditions)cache.get(p).getConditions();

		wC.setIfWeatherMsg(cache.get(p).getMessage(WorldConditionsMessages.IF_WEATHER_MSG.name));
		wC.setIfWorldTimeMsg(cache.get(p).getMessage(WorldConditionsMessages.IF_WORLD_TIME_MSG.name));
		
		WorldConditions.saveWorldConditions(sPlugin, sObject, sActivator, wC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static WorldConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new WorldConditionsMessagesGUIManager();
		return instance;
	}
}
