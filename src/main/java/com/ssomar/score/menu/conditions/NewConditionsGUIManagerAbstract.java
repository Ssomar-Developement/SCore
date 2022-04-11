package com.ssomar.score.menu.conditions;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NewConditionsGUIManagerAbstract<G extends NewConditionsGUIAbstract, T extends NewConditions, Y extends Condition, Z extends ConditionsManager<T, Y>> extends GUIManagerSCore<G> {

	@Getter
	private Z conditionsManager;
	public NewConditionsGUIManagerAbstract(Z conditionsManager) {
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

		Boolean found = false;

		for(Condition condition : conditionsManager.getConditions().values()){

			if(i.name.contains(condition.getEditorName())) {
				switch (condition.getConditionType()) {
					case BOOLEAN:
						i.gui.changeBoolean(condition.getEditorName());
						break;
					case NUMBER_CONDITION:
						requestWriting.put(i.player, condition.getEditorName()+"::"+ConditionType.NUMBER_CONDITION);
						i.player.closeInventory();
						space(i.player);
						i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION "+condition.getEditorName()+":"));

						this.showCalculationGUI(i.player, "Condition", cache.get(i.player).getCondition(condition.getEditorName()));
						space(i.player);
						break;
					case CUSTOM_AROUND_BLOCK:
						break;
					case LIST_WEATHER:
						requestWriting.put(i.player, condition.getEditorName()+"::"+ConditionType.LIST_WEATHER);
						if(!currentWriting.containsKey(i.player)) {
							currentWriting.put(i.player, cache.get(i.player).getConditionList(condition.getEditorName(), "NO WEATHER IS REQUIRED"));
						}
						i.player.closeInventory();
						space(i.player);
						i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION "+condition.getEditorName()+":"));

						this.showIfWeatherEditor(i.player);
						space(i.player);
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
		String setting = requestWriting.get(p).split("::")[0];
		String category = requestWriting.get(p).split("::")[1];

		if(message.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {

				if(category.equals(ConditionType.LIST_WEATHER.toString())) {
					cache.get(p).updateConditionList(setting, new ArrayList<>(), "&6➤ &eNO WEATHER IS REQUIRED");
				}
				else if(category.equals(ConditionType.NUMBER_CONDITION)) {
					cache.get(p).updateCondition(setting, "");
				}

				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				pass = true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {

				if(category.equals(ConditionType.LIST_WEATHER.toString())) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateConditionList(setting, result, "&6➤ &eNO WEATHER IS REQUIRED");
				}

				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit = false;
			}
		}
		if(notExit) {

			String editMessage = StringConverter.decoloredString(message.trim());

			if(editMessage.contains("delete line <")) {
				this.deleteLine(editMessage, p);
				if(category.equals(ConditionType.LIST_WEATHER.toString())) this.showIfWeatherEditor(p);
				space(p);
				space(p);
			}
			else if(category.equals(ConditionType.NUMBER_CONDITION.toString())){
				if(StringCalculation.isStringCalculation(editMessage)) {
					cache.get(p).updateCondition(setting, editMessage);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition please !"));
					this.showCalculationGUI(p, "Condition", cache.get(p).getCondition(requestWriting.get(p)));
				}
			}
			else if(category.equals(ConditionType.LIST_WEATHER.toString())) {
				if(!editMessage.isEmpty()) {
					editMessage = editMessage.toUpperCase();
					if(editMessage.equals("CLEAR") || editMessage.equals("STORM") || editMessage.equals("RAIN") ) {
						if(currentWriting.containsKey(p)) currentWriting.get(p).add(editMessage);
						else {
							ArrayList<String> list = new ArrayList<>();
							list.add(editMessage);
							currentWriting.put(p, list);
						}
						p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION &aYou have added new weather!"));
					}
					else p.sendMessage(StringConverter.coloredString("&c&l[ExecutableItems] &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));
				}
				else p.sendMessage(StringConverter.coloredString("&c&l[ExecutableItems] &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));

				this.showIfWeatherEditor(p);
			}
		}

	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<G> interact) {
		return false;
	}

	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		T bC = (T) cache.get(p).getConditions();

		for(Condition condition : conditionsManager.getConditions().values()){
			Condition modifiedCondition;
			if (bC.contains(condition)) modifiedCondition = bC.get(condition);
			else modifiedCondition = (Condition) condition.clone();
			switch (condition.getConditionType()) {

				case BOOLEAN:
					modifiedCondition.setCondition(cache.get(p).getBoolean(condition.getEditorName()));
					break;
				case NUMBER_CONDITION:
					modifiedCondition.setCondition(cache.get(p).getCondition(condition.getEditorName()));
					break;
				case CUSTOM_AROUND_BLOCK:
					break;
				case LIST_WEATHER:
					modifiedCondition.setCondition(cache.get(p).getConditionList(condition.getEditorName(), "NO WEATHER IS REQUIRED"));
					break;
			}
			bC.add(modifiedCondition);
		}

		conditionsManager.saveConditions(sPlugin, sObject, sAct, bC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}


	public void showIfWeatherEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifWeather: (RAIN, CLEAR, or STORM)");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Weather", false, false, false, true, true, true, false, "", suggestions);
		editor.generateTheMenuAndSendIt(p);
	}
}
