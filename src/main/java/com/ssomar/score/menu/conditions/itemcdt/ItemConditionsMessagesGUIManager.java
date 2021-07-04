package com.ssomar.score.menu.conditions.itemcdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
import com.ssomar.score.utils.StringConverter;


public class ItemConditionsMessagesGUIManager extends GUIManagerSCore<ItemConditionsMessagesGUI>{

	private static ItemConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions iC, String detail) {
		cache.put(p, new ItemConditionsMessagesGUI(sPlugin, sObject, sActivator, iC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public void clicked(InteractionClickedGUIManager<ItemConditionsMessagesGUI> i) {
		
		if(i.name.contains("Save")) {
			saveItemConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else if(!i.name.isEmpty()) {
			for(ItemConditionsMessages icMsg : ItemConditionsMessages.values()) {
				if(i.name.contains(icMsg.name)) {
					requestWriting.put(i.player, icMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(icMsg.name);
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
					cache.replace(p, new ItemConditionsMessagesGUI(sPlugin, sObject, sAct, new ItemConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					saveItemConditionsEI(p);
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
					saveItemConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					ItemConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getItemConditions(), detail);
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
