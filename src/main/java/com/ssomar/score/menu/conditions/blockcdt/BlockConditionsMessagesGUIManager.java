package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.blockcdt.BlockConditionsMessagesGUI.BlockConditionsMessages;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;


public class BlockConditionsMessagesGUIManager extends GUIManagerConditions<BlockConditionsMessagesGUI>{

	private static BlockConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsMessagesGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		
		BlockConditions bC;
		if(detail.contains("target")) bC = i.sObject.getActivator(i.sActivator.getID()).getTargetBlockConditions();
		else bC = i.sObject.getActivator(i.sActivator.getID()).getBlockConditions();
		BlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC, detail);
		return true;
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		if(!i.name.isEmpty()) {
			for(BlockConditionsMessages bcMsg : BlockConditionsMessages.values()) {
				if(i.name.contains(bcMsg.name)) {
					requestWriting.put(i.player, bcMsg.name);
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(bcMsg.name);
					RequestMessage.sendRequestMessage(i.msgInfos);
					return true;
				}
			}
		}
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


	public static BlockConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new BlockConditionsMessagesGUIManager();
		return instance;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<BlockConditionsMessagesGUI> interact) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		BlockConditions bC = (BlockConditions) cache.get(p).getConditions();

		bC.setIfIsPoweredMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_IS_POWERED_MSG.name));
		bC.setIfMustBeNotPoweredMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_MUST_BE_NOT_POWERED_MSG.name));
		bC.setIfPlantFullyGrownMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_PLANT_FULLY_GROWN_MSG.name));
		bC.setIfIsNaturalMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_MUST_BE_NATURAL_MSG.name));
		bC.setIfBlockLocationXMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_X_MSG.name));
		bC.setIfBlockLocationX2Msg(cache.get(p).getMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_X2_MSG.name));
		bC.setIfBlockLocationYMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Y_MSG.name));
		bC.setIfBlockLocationY2Msg(cache.get(p).getMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Y2_MSG.name));
		bC.setIfBlockLocationZMsg(cache.get(p).getMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Z_MSG.name));
		bC.setIfBlockLocationZ2Msg(cache.get(p).getMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Z2_MSG.name));
		
		BlockConditions.saveBlockConditions(sPlugin, sObject, sActivator, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}

	
}
