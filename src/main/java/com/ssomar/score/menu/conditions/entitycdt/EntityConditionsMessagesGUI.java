package com.ssomar.score.menu.conditions.entitycdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;

public class EntityConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public EntityConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Entity Conditions Messages", 4*9, sPlugin, sObject, sAct, detail, conditions);
	}
	
	@Override
	public void loadTheGUI() {
		EntityConditions conditions = (EntityConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_GLOWING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_GLOWING_MSG.name, conditions.getIfGlowingMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_ADULT_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_ADULT_MSG.name, conditions.getIfAdultMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_BABY_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_BABY_MSG.name, conditions.getIfBabyMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_INVULNERABLE_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_INVULNERABLE_MSG.name, conditions.getIfInvulnerableMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_POWERED_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_POWERED_MSG.name, conditions.getIfPoweredMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_NAME_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_NAME_MSG.name, conditions.getIfNameMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_NOT_ENTITY_TYPE_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_NOT_ENTITY_TYPE_MSG.name, conditions.getIfNotEntityTypeMsg());
				
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+EntityConditionsMessages.IF_ENTITY_HEALTH_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(EntityConditionsMessages.IF_ENTITY_HEALTH_MSG.name, conditions.getIfEntityHealthMsg());
		
		createItem(RED, 			1 , 27, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 			1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of entity conditions" );
		
		createItem(GREEN, 			1 , 35, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of entity conditions" );
		
		createItem(WRITABLE_BOOK, 	1 , 31, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 33, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 	1 , 34, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}
	
	public enum EntityConditionsMessages{
		IF_GLOWING_MSG ("ifGlowing message"),
		IF_ADULT_MSG ("ifAdult message"),
		IF_BABY_MSG ("ifBaby message"),
		IF_INVULNERABLE_MSG ("ifInvulnerable message"),
		IF_POWERED_MSG ("ifPowered message"),
		IF_NAME_MSG ("ifName message"),
		IF_NOT_ENTITY_TYPE_MSG ("ifNotEntityType message"),
		IF_ENTITY_HEALTH_MSG ("ifEntityHealth message");
		
		public String name;

		EntityConditionsMessages(String name) {
			this.name = name;
		}
	}
}
