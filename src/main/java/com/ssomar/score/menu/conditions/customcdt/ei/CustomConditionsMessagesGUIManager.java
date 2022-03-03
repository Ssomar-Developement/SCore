package com.ssomar.score.menu.conditions.customcdt.ei;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.customcdt.ei.CustomConditionsMessagesGUI.CustomConditionsMessages;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.splugin.SPlugin;


public class CustomConditionsMessagesGUIManager extends GUIManagerConditions<CustomConditionsMessagesGUI>{

	private static CustomConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, CustomEIConditions cC, String detail) {
		cache.put(p, new CustomConditionsMessagesGUI(sPlugin, sObject, sActivator, cC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		if(!i.name.isEmpty()) {
			for(CustomConditionsMessages ccMsg : CustomConditionsMessages.values()) {
				if(i.name.contains(ccMsg.name)) {
					requestWriting.put(i.player, ccMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(ccMsg.name);
					RequestMessage.sendRequestMessage(i.msgInfos);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
		CustomConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getCustomEIConditions(), detail);
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<CustomConditionsMessagesGUI> interact) {
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


	public static CustomConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new CustomConditionsMessagesGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		CustomEIConditions cC = (CustomEIConditions) cache.get(p).getConditions();

		cC.setIfNeedPlayerConfirmationMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_NEED_PLAYER_CONFIRMATION_MSG.name));
		cC.setIfNotOwnerOfTheEIMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_NOT_OWNER_OF_THE_EI_MSG.name));
		cC.setIfOwnerOfTheEIMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_OWNER_OF_THE_EI_MSG.name));
		cC.setIfPlayerMustBeOnHisIslandMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG.name));
		cC.setIfPlayerMustBeOnHisClaimMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG.name));
		cC.setIfPlayerMustBeOnHisPlotMsg(cache.get(p).getMessage(CustomConditionsMessages.IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG.name));
		
		CustomEIConditions.saveCustomConditions(sPlugin, sObject, sActivator, cC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}
}
