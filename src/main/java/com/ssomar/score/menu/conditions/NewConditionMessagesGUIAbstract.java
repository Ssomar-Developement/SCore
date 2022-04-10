package com.ssomar.score.menu.conditions;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.sobject.sactivator.conditions.NewConditions;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import com.ssomar.score.sobject.sactivator.conditions.managers.ConditionsManager;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter @Setter
public abstract class NewConditionMessagesGUIAbstract<T extends NewConditions, Y extends Condition, Z extends ConditionsManager<T, Y>> extends GUIAbstract{

	private String detail;
	private T conditions;
	private Z conditionsManager;

	public NewConditionMessagesGUIAbstract(String name, int size, SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, T conditions, Z conditionsManager) {
		super(name, size, sPlugin, sObject, sAct);	
		this.conditions = conditions;
		this.conditionsManager = conditionsManager;
		this.detail = detail;
		this.loadTheGUI();
	}

	@Override
	public void reloadGUI() {
		this.loadTheGUI();
	}
	
	public void updateMessage(String itemName, String message) {
		if (message.isEmpty() || message.equals(" ")) this.updateActually(itemName, "&cNO MESSAGE");
		else this.updateActually(itemName, message);
	}
	
	public String getMessage(String itemName) {
		String msg = this.getActuallyWithColor(itemName);
		
		if (msg.contains("NO MESSAGE")) return "";
		else return msg;
	}

	public void loadTheGUI() {
		T conditions = (T) this.getConditions();
		int i = 0;

		for(Condition condition : conditionsManager.getConditions().values()){

			createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+condition.getEditorName()+" message", 	false,	false, "&a✎ Click here to change", "&7actually:");
			i++;
			if(conditions.contains(condition)){
				Condition loadedCondition = conditions.get(condition);
				if(loadedCondition.getCustomErrorMsg().isPresent()){
					this.updateMessage(condition.getEditorName()+" message", (String) loadedCondition.getCustomErrorMsg().get());
				}
				else this.updateMessage(condition.getEditorName()+" message", "");
			}
			else this.updateMessage(condition.getEditorName()+" message", "");
		}

		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);

		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall thses conditions" );

		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of these conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");

		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}

}
