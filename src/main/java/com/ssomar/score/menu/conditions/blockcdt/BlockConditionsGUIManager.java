package com.ssomar.score.menu.conditions.blockcdt;

import com.ssomar.score.menu.conditions.itemcdt.ItemConditionsGUI;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

public class BlockConditionsGUIManager extends GUIManagerConditions<BlockConditionsGUI>{

	private static BlockConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {
		cache.put(p, new BlockConditionsGUI(sPlugin, sObject, sActivator, bC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		return this.saveOrBackOrNothingNEW(i);
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
		
		BlockConditions bC;
		if(detail.contains("target")) bC = i.sObject.getActivator(i.sActivator.getID()).getTargetBlockConditions();
		else bC = i.sObject.getActivator(i.sActivator.getID()).getBlockConditions();
			
		// TODO BlockConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC, detail);
		return true;
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<BlockConditionsGUI> i) {
		
		Boolean found = false;

		for(Condition condition : BlockConditionsManager.getInstance().getConditions().values()){

			if(i.name.contains(condition.getEditorName())) {
				switch (condition.getConditionType()) {
					case BOOLEAN:
						i.gui.changeBoolean(condition.getEditorName());
						break;
					case NUMBER_CONDITION:
						requestWriting.put(i.player, condition.getEditorName());
						i.player.closeInventory();
						space(i.player);
						i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION "+condition.getEditorName()+":"));

						this.showCalculationGUI(i.player, "Age", cache.get(i.player).getCondition(condition.getEditorName()));
						space(i.player);
						break;
					case CUSTOM_AROUND_BLOCK:
						break;
				}
				found = true;
				break;
			}
		}
		/* else if(i.name.contains(BlockConditionsGUI.AROUND_BLOCK_CDT)) {
			AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, bC.getBlockAroundConditions(), cache.get(i.player).getDetail());
		}*/
		if(!found) return false;

		return true;
	}
	
	public void receivedMessage(Player p, String message) {
		boolean notExit = true;
		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {

				cache.get(p).updateCondition(requestWriting.get(p), "");
				
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				pass = true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit=false;
			}
		}
		if(notExit) {
			String editMessage = StringConverter.decoloredString(message.trim());
			if(StringCalculation.isStringCalculation(editMessage)) {
				cache.get(p).updateCondition(requestWriting.get(p), editMessage);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
			}
			else {
				p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition please !"));
				this.showCalculationGUI(p, "Condition", cache.get(p).getCondition(requestWriting.get(p)));
			}
		}

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

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		BlockConditions bC = (BlockConditions) cache.get(p).getConditions();

		for(Condition condition : BlockConditionsManager.getInstance().getConditions().values()){
			Condition cloneCondition = (Condition) condition.clone();
			switch (condition.getConditionType()) {

				case BOOLEAN:
					cloneCondition.setCondition(cache.get(p).getBoolean(condition.getEditorName()));
					break;
				case NUMBER_CONDITION:
					cloneCondition.setCondition(cache.get(p).getCondition(condition.getEditorName()));
					break;
				case CUSTOM_AROUND_BLOCK:
					break;
			}
			bC.add(cloneCondition);
		}

		BlockConditionsManager.getInstance().saveConditions(sPlugin, sObject, sAct, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}

}
