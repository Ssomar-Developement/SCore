package com.ssomar.score.menu.conditions;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class NewConditionsMessagesGUIManager<G extends NewConditionMessagesGUIAbstract, T extends NewConditions, Y extends Condition, Z extends ConditionsManager<T, Y>> extends GUIManagerSCore<G> {

	@Getter
	private Z conditionsManager;
	public NewConditionsMessagesGUIManager(Z conditionsManager) {
		this.conditionsManager = conditionsManager;
	}

	public boolean saveOrBackOrNothingNEW(InteractionClickedGUIManager<G> i) {
		if(i.name.contains("Save")) {
			this.saveTheConfiguration(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else return false;

		return true;
	}

	@Override
	public boolean allClicked(InteractionClickedGUIManager<G> i) {
		return this.saveOrBackOrNothingNEW(i);
	}


	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<G> i) {
		if(!i.name.isEmpty()) {
			for(Condition condition : conditionsManager.getConditions().values()){
				if(i.name.contains(condition.getEditorName()+" message")) {
					requestWriting.put(i.player, condition.getEditorName()+" message");
					i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(condition.getEditorName()+" message");
					RequestMessage.sendRequestMessage(i.msgInfos);
					return true;
				}
			}
		}
		return false;
	}

	public void receivedMessage(Player p, String message) {
		String request = requestWriting.get(p);

		if(message .contains("NO MESSAGE")) cache.get(p).updateMessage(request, "");
		else cache.get(p).updateMessage(request, message);
		requestWriting.remove(p);
		cache.get(p).openGUISync(p);
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<G> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<G> i) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<G> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<G> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<G> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	public <T extends NewConditions, Y extends Condition, Z extends ConditionsManager<T, Y>> void saveTheConfiguration(Player p, Z conditionsManager){
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		T bC = (T) cache.get(p).getConditions();

		for(Condition condition : conditionsManager.getConditions().values()){
			Condition modifiedCondition;
			if (bC.contains(condition)) modifiedCondition = bC.get(condition);
			else modifiedCondition = (Condition) condition.clone();

			modifiedCondition.setCustomErrorMsg(Optional.ofNullable(cache.get(p).getMessage(condition.getEditorName() + " message")));

			bC.add(modifiedCondition);
		}

		conditionsManager.saveConditions(sPlugin, sObject, sActivator, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}
}
