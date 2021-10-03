package com.ssomar.score.menu.conditions.worldcdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.worldcdt.WorldConditionsMessagesGUI.WorldConditionsMessages;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;


public class WorldConditionsMessagesGUIManager extends GUIManagerConditions<WorldConditionsMessagesGUI>{

	private static WorldConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions wC, String detail) {
		cache.put(p, new WorldConditionsMessagesGUI(sPlugin, sObject, sActivator, wC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		if(!i.name.isEmpty()) {
			for(WorldConditionsMessages wcMsg : WorldConditionsMessages.values()) {
				if(i.name.contains(wcMsg.name)) {
					requestWriting.put(i.player, wcMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(wcMsg.name);
					RequestMessage.sendRequestMessage(i.msgInfos);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		WorldConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getWorldConditions(), detail);
	
		return true;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<WorldConditionsMessagesGUI> interact) {
		return false;
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


	public static WorldConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new WorldConditionsMessagesGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
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
}
