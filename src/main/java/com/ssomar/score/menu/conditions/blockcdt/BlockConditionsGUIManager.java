package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt.AroundBlockConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class BlockConditionsGUIManager extends GUIManagerSCore<BlockConditionsGUI>{

	private static BlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public void clicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		
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
		
		else if(i.name.contains("Save")) {
			saveBlockConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
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
					cache.replace(p, new BlockConditionsGUI(sPlugin, sObject, sAct, new BlockConditions(), cache.get(p).getDetail()));
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
					if(detail.contains("target")) BlockConditionsMessagesGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getTargetBlockConditions(), detail);
					else BlockConditionsMessagesGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getBlockConditions(), detail);
				}
			}
		}
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

}
