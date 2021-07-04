package com.ssomar.score.menu.conditions.entitycdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.entitycdt.EntityConditionsMessagesGUI.EntityConditionsMessages;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class EntityConditionsMessagesGUIManager extends GUIManagerSCore<EntityConditionsMessagesGUI>{

	private static EntityConditionsMessagesGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		cache.put(p, new EntityConditionsMessagesGUI(sPlugin, sObject, sAct, conditions, detail));
		cache.get(p).openGUISync(p);
	}

	@Override
	public void clicked(InteractionClickedGUIManager<EntityConditionsMessagesGUI> i) {
		
		if(i.name.contains("Save")) {
			saveEntityConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else if(!i.name.isEmpty()) {
			for(EntityConditionsMessages ecMsg : EntityConditionsMessages.values()) {
				if(i.name.contains(ecMsg.name)) {
					requestWriting.put(i.player, ecMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(ecMsg.name);
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
					p.closeInventory();
					cache.replace(p, new EntityConditionsMessagesGUI(sPlugin, sObject, sAct, new EntityConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					p.closeInventory();
					saveEntityConditionsEI(p);
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
					saveEntityConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					EntityConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getTargetEntityConditions(), detail);
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

	public void saveEntityConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		
		EntityConditions eC = (EntityConditions) cache.get(p).getConditions();
		eC.setIfAdultMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_ADULT_MSG.name));
		eC.setIfBabyMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_BABY_MSG.name));
		eC.setIfEntityHealthMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_ENTITY_HEALTH_MSG.name));
		eC.setIfGlowingMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_GLOWING_MSG.name));
		eC.setIfInvulnerableMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_INVULNERABLE_MSG.name));
		eC.setIfNameMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_NAME_MSG.name));
		eC.setIfNotEntityTypeMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_NOT_ENTITY_TYPE_MSG.name));
		eC.setIfPoweredMsg(cache.get(p).getMessage(EntityConditionsMessages.IF_POWERED_MSG.name));

		EntityConditions.saveEntityConditions(sPlugin, sObject, sActivator, eC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static EntityConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new EntityConditionsMessagesGUIManager();
		return instance;
	}
}
