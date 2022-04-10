package com.ssomar.score.menu.conditions.blockcdt;

import com.ssomar.score.menu.conditions.NewConditionGUIAbstract;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.BlockCondition;
import com.ssomar.score.sobject.sactivator.conditions.managers.BlockConditionsManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsGUI extends NewConditionGUIAbstract {

	public static final String AROUND_BLOCK_CDT = "Around block conditions";
	
	public BlockConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Block Conditions", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}
	
	@Override
	public void loadTheGUI() {
		BlockConditions conditions = (BlockConditions)this.getConditions();

		int i = 0;
		for(Condition condition : BlockConditionsManager.getInstance().getConditions().values()){

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
			}
		}

		
		//createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
		//i++;


		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of block conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of block conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 			1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 			1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}
}
