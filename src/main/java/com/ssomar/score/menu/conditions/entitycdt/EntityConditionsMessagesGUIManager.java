package com.ssomar.score.menu.conditions.entitycdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.entitycdt.EntityConditionsMessagesGUI.EntityConditionsMessages;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;

public class EntityConditionsMessagesGUIManager extends GUIManagerConditions<EntityConditionsMessagesGUI>{

	private static EntityConditionsMessagesGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		cache.put(p, new EntityConditionsMessagesGUI(sPlugin, sObject, sAct, conditions, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		if(!i.name.isEmpty()) {
			for(EntityConditionsMessages ecMsg : EntityConditionsMessages.values()) {
				if(i.name.contains(ecMsg.name)) {
					requestWriting.put(i.player, ecMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(ecMsg.name);
					RequestMessage.sendRequestMessage(i.msgInfos);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
		EntityConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getTargetEntityConditions(), detail);
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> interact) {
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

	public static EntityConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new EntityConditionsMessagesGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		
		EntityConditions eC = (EntityConditions) cache.get(p).getConditions();
		eC.setIfAdultMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_ADULT_MSG.name));
		eC.setIfBabyMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_BABY_MSG.name));
		eC.setIfEntityHealthMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_ENTITY_HEALTH_MSG.name));
		eC.setIfGlowingMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_GLOWING_MSG.name));
		eC.setIfInvulnerableMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_INVULNERABLE_MSG.name));
		eC.setIfOnFireMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_ON_FIRE_MSG.name));
		eC.setIfNameMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_NAME_MSG.name));
		eC.setIfNotEntityTypeMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_NOT_ENTITY_TYPE_MSG.name));
		eC.setIfPoweredMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_POWERED_MSG.name));

		EntityConditions.saveEntityConditions(sPlugin, sObject, sActivator, eC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}
}
