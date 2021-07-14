package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.blockcdt.BlockConditionsMessagesGUI.BlockConditionsMessages;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class BlockConditionsMessagesGUIManager extends GUIManagerSCore<BlockConditionsMessagesGUI>{

	private static BlockConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsMessagesGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public void clicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		
		if(i.name.contains("Save")) {
			saveBlockConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else if(!i.name.isEmpty()) {
			for(BlockConditionsMessages bcMsg : BlockConditionsMessages.values()) {
				if(i.name.contains(bcMsg.name)) {
					requestWriting.put(i.player, bcMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(bcMsg.name);
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
					cache.replace(p, new BlockConditionsMessagesGUI(sPlugin, sObject, sAct, new BlockConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					saveBlockConditionsEI(p);
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
					saveBlockConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					if(detail.contains("target")) BlockConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getTargetBlockConditions(), detail);
					else BlockConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getBlockConditions(), detail);
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

	public void saveBlockConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		BlockConditions bC = (BlockConditions) cache.get(p).getConditions();

		bC.setIfIsPoweredMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_IS_POWERED_MSG.name));
		bC.setIfPlantFullyGrownMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_PLANT_FULLY_GROWN_MSG.name));
		
		BlockConditions.saveBlockConditions(sPlugin, sObject, sActivator, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static BlockConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new BlockConditionsMessagesGUIManager();
		return instance;
	}
}
