package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public BlockConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Item Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}

	@Override
	public void loadTheGUI() {
		BlockConditions conditions = (BlockConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_PLANT_FULLY_GROWN_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_PLANT_FULLY_GROWN_MSG.name, conditions.getIfPlantFullyGrownMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_IS_POWERED_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_IS_POWERED_MSG.name, conditions.getIfIsPoweredMsg());
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of block conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of block conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}	
	
	public enum BlockConditionsMessages{
		IF_PLANT_FULLY_GROWN_MSG ("ifPlantFullyGrown message"),
		IF_IS_POWERED_MSG ("ifIsPowered message");
		
		public String name;

		BlockConditionsMessages(String name) {
			this.name = name;
		}
	}
}
