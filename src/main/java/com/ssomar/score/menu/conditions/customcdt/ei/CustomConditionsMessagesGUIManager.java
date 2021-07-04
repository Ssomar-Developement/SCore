package com.ssomar.score.menu.conditions.customcdt.ei;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.customcdt.ei.CustomConditionsMessagesGUI.CustomConditionsMessages;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class CustomConditionsMessagesGUIManager extends GUIManagerSCore<CustomConditionsMessagesGUI>{

	private static CustomConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, CustomEIConditions cC, String detail) {
		cache.put(p, new CustomConditionsMessagesGUI(sPlugin, sObject, sActivator, cC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public void clicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		
		if(i.name.contains("Save")) {
			saveCustomConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else if(!i.name.isEmpty()) {
			for(CustomConditionsMessages ccMsg : CustomConditionsMessages.values()) {
				if(i.name.contains(ccMsg.name)) {
					requestWriting.put(i.player, ccMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(ccMsg.name);
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
					cache.replace(p, new CustomConditionsMessagesGUI(sPlugin, sObject, sAct, new CustomEIConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
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
				else {
					String detail = cache.get(p).getDetail();
					saveCustomConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					CustomConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getCustomEIConditions(), detail);
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

	public void saveCustomConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		CustomEIConditions cC = (CustomEIConditions) cache.get(p).getConditions();

		cC.setIfNeedPlayerConfirmationMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_NEED_PLAYER_CONFIRMATION_MSG.name));
		cC.setIfNotOwnerOfTheEIMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_NOT_OWNER_OF_THE_EI_MSG.name));
		cC.setIfOwnerOfTheEIMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_OWNER_OF_THE_EI_MSG.name));
		cC.setIfPlayerMustBeOnHisIslandMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG.name));
		cC.setIfPlayerMustBeOnHisClaimMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG.name));
		
		CustomEIConditions.saveCustomConditions(sPlugin, sObject, sActivator, cC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static CustomConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new CustomConditionsMessagesGUIManager();
		return instance;
	}
}
