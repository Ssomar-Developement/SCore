package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt.AroundBlockConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsGUIManager extends GUIManagerSCore<BlockConditionsGUI>{

	private static BlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		if(i.name.contains("Save")) {
			saveBlockConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
			return true;
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		saveBlockConditionsEI(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		
		BlockConditions bC;
		if(detail.contains("target")) bC = i.sObject.getActivator(i.sActivator.getID()).getTargetBlockConditions();
		else bC = i.sObject.getActivator(i.sActivator.getID()).getBlockConditions();
			
		BlockConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC, detail);
		return true;
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		
		BlockConditions bC = (BlockConditions) cache.get(i.player).getConditions();
		
		if(i.name.contains(BlockConditionsGUI.IF_IS_POWERED)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_IS_POWERED);
		}
		else if(i.name.contains(BlockConditionsGUI.IF_PLANT_FULLY_GROWN)) {
			i.gui.changeBoolean(BlockConditionsGUI.IF_PLANT_FULLY_GROWN);
		}
		else if(i.name.contains(BlockConditionsGUI.AROUND_BLOCK_CDT)) {
			AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC.getBlockAroundConditions(), cache.get(i.player).getDetail());
		}
		else return false;
		
		return true;
	}

	public void saveBlockConditionsEI(Player p) {

		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		BlockConditions bC = (BlockConditions) cache.get(p).getConditions();

		bC.setIfIsPowered(cache.get(p).getBoolean(BlockConditionsGUI.IF_IS_POWERED));
		bC.setIfPlantFullyGrown(cache.get(p).getBoolean(BlockConditionsGUI.IF_PLANT_FULLY_GROWN));

		BlockConditions.saveBlockConditions(sPlugin, sObject, sAct, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static BlockConditionsGUIManager getInstance() {
		if(instance == null) instance = new BlockConditionsGUIManager();
		return instance;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<BlockConditionsGUI> interact) {
		return false;
	}

}
