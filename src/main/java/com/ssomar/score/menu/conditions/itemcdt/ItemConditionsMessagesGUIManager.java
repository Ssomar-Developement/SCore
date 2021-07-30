package com.ssomar.score.menu.conditions.itemcdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.itemcdt.ItemConditionsMessagesGUI.ItemConditionsMessages;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;


public class ItemConditionsMessagesGUIManager extends GUIManagerSCore<ItemConditionsMessagesGUI>{

	private static ItemConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions iC, String detail) {
		cache.put(p, new ItemConditionsMessagesGUI(sPlugin, sObject, sActivator, iC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		if(i.name.contains("Save")) {
			saveItemConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		if(!i.name.isEmpty()) {
			for(ItemConditionsMessages icMsg : ItemConditionsMessages.values()) {
				if(i.name.contains(icMsg.name)) {
					requestWriting.put(i.player, icMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(icMsg.name);
					RequestMessage.sendRequestMessage(i.msgInfos);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		String detail = cache.get(i.player).getDetail();
		saveItemConditionsEI(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		ItemConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getItemConditions(), detail);
	
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> interact) {
		// TODO Auto-generated method stub
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

	public void saveItemConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		ItemConditions iC = (ItemConditions) cache.get(p).getConditions();

		iC.setIfDurabilityMsg(cache.get(p).getMessage(ItemConditionsMessages.IF_DURABILITY_MSG.name));
		iC.setIfUsageMsg(cache.get(p).getMessage(ItemConditionsMessages.IF_USAGE_MSG.name));
		iC.setIfUsage2Msg(cache.get(p).getMessage(ItemConditionsMessages.IF_USAGE2_MSG.name));
		
		ItemConditions.saveItemConditions(sPlugin, sObject, sActivator, iC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static ItemConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new ItemConditionsMessagesGUIManager();
		return instance;
	}
}
