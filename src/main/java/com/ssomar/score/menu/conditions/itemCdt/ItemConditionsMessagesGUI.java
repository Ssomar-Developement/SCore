package com.ssomar.score.menu.conditions.itemCdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;

public class ItemConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public static final String IF_DURABILITY_MSG = "ifDurability message";
	public static final String IF_USAGE_MSG = "ifUsage message";
	public static final String IF_USAGE2_MSG = "ifUsage2 message";

	private ItemConditions conditions;
	
	public ItemConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Item Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail);
		this.conditions = conditions;

		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_DURABILITY_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_DURABILITY_MSG, conditions.getIfDurabilityMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_USAGE_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_USAGE_MSG, conditions.getIfUsageMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_USAGE2_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_USAGE2_MSG, conditions.getIfUsage2Msg());
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of item conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of item conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sActivator.getID());
	
	}

	public ItemConditions getConditions() {
		return conditions;
	}	
}
