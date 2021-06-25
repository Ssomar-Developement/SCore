package com.ssomar.score.menu.conditions.entityCdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;

public class EntityConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public static final String IF_GLOWING_MSG = "ifGlowing message";
	public static final String IF_ADULT_MSG = "ifAdult message";
	public static final String IF_BABY_MSG = "ifBaby message";
	public static final String IF_INVULNERABLE_MSG = "ifInvulnerable message";
	public static final String IF_POWERED_MSG = "ifPowered message";
	public static final String IF_NAME_MSG = "ifName message";
	public static final String IF_NOT_ENTITY_TYPE_MSG = "ifNotEntityType message";
	public static final String IF_ENTITY_HEALTH_MSG = "ifEntityHealth message";
	
	private EntityConditions conditions;

	public EntityConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Entity Conditions Messages", 4*9, sPlugin, sObject, sAct, detail);
		this.conditions = conditions;
		
		int i =0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_GLOWING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_GLOWING_MSG, conditions.getIfGlowingMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_ADULT_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_ADULT_MSG, conditions.getIfAdultMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_BABY_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_BABY_MSG, conditions.getIfBabyMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_INVULNERABLE_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_INVULNERABLE_MSG, conditions.getIfInvulnerableMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_POWERED_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_POWERED_MSG, conditions.getIfPoweredMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NAME_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NAME_MSG, conditions.getIfNameMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_ENTITY_TYPE_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_ENTITY_TYPE_MSG, conditions.getIfNotEntityTypeMsg());
				
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_ENTITY_HEALTH_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_ENTITY_HEALTH_MSG, conditions.getIfEntityHealthMsg());
		
		createItem(RED, 			1 , 27, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 			1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of entity conditions" );
		
		createItem(GREEN, 			1 , 35, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of entity conditions" );
		
		createItem(WRITABLE_BOOK, 	1 , 31, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 33, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 34, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sAct.getID());
	}	

	public EntityConditions getConditions() {
		return conditions;
	}
}
