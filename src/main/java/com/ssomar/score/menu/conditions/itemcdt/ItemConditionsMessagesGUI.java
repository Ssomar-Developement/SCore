package com.ssomar.score.menu.conditions.itemcdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;

public class ItemConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public ItemConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Item Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}

	@Override
	public void loadTheGUI() {
		ItemConditions conditions = (ItemConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+ItemConditionsMessages.IF_DURABILITY_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(ItemConditionsMessages.IF_DURABILITY_MSG.name, conditions.getIfDurabilityMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+ItemConditionsMessages.IF_USAGE_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(ItemConditionsMessages.IF_USAGE_MSG.name, conditions.getIfUsageMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+ItemConditionsMessages.IF_USAGE2_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(ItemConditionsMessages.IF_USAGE2_MSG.name, conditions.getIfUsage2Msg());
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of item conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of item conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}	
	
	public enum ItemConditionsMessages{
		IF_DURABILITY_MSG ("ifDurability message"),
		IF_USAGE_MSG ("ifUsage message"),
		IF_USAGE2_MSG ("ifUsage2 message");
		
		public String name;

		ItemConditionsMessages(String name) {
			this.name = name;
		}
	}
}
