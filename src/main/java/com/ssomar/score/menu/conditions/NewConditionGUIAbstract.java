package com.ssomar.score.menu.conditions;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.sobject.sactivator.conditions.Conditions;
import com.ssomar.score.sobject.sactivator.conditions.NewConditions;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import com.ssomar.score.sobject.sactivator.conditions.managers.ConditionsManager;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class NewConditionGUIAbstract<T extends NewConditions, Y extends Condition, Z extends ConditionsManager<T, Y>> extends GUIAbstract{

	private String detail;
	private T conditions;
	private Z conditionsManager;

	public NewConditionGUIAbstract(String name, int size, SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, T conditions, Z conditionsManager) {
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

	public void loadTheGUI() {
		T conditions = this.getConditions();

		int i = 0;
		for(Condition condition : conditionsManager.getConditions().values()){

			String [] finalDescription = null;

			switch(condition.getConditionType()){

				case BOOLEAN: case NUMBER_CONDITION:
					finalDescription = new String[condition.getEditorDescription().length+2];
					for(int j = 0; j < condition.getEditorDescription().length; j++){
						finalDescription[j] = condition.getEditorDescription()[j];
					}
					finalDescription[finalDescription.length-2] = "&a✎ Click here to change";
					finalDescription[finalDescription.length-1] = "&7actually:";
					break;
				case CUSTOM_AROUND_BLOCK:
					break;
				case WEATHER_LIST:
					finalDescription = new String[condition.getEditorDescription().length+3];
					for(int j = 0; j < condition.getEditorDescription().length; j++){
						finalDescription[j] = condition.getEditorDescription()[j];
					}
					finalDescription[finalDescription.length-3] = "&a✎ Click here to change";
					finalDescription[finalDescription.length-2] = "&7actually:";
					finalDescription[finalDescription.length-1] = "";
					break;
			}

			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+condition.getEditorName(), 	false,	false, finalDescription);
			i++;

			switch(condition.getConditionType()){

				case BOOLEAN:
					if(conditions.contains(condition)){
						this.updateBoolean(condition.getEditorName(), (Boolean)conditions.get(condition).getCondition());
					}
					else this.updateBoolean(condition.getEditorName(), false);
					break;
				case NUMBER_CONDITION:
					if(conditions.contains(condition)){
						this.updateCondition(condition.getEditorName(), (String)conditions.get(condition).getCondition());
					}
					else this.updateCondition(condition.getEditorName(), "");
					break;
				case CUSTOM_AROUND_BLOCK:
					break;

				case WEATHER_LIST:
					if(conditions.contains(condition)) {
						this.updateConditionList(condition.getEditorName(), (List<String>) conditions.get(condition).getCondition(), "&6➤ &eNO WEATHER IS REQUIRED");
					}
					else this.updateConditionList(condition.getEditorName(), new ArrayList<>(), "&6➤ &eNO WEATHER IS REQUIRED");
					break;
			}
		}

		//createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
		//					i++;

		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);

		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall these conditions" );

		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of these conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");

		createItem(Material.BOOK, 			1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 			1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}

}
